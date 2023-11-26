package hu.unideb.inf.NeptunPro.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import hu.unideb.inf.NeptunPro.domain.model.grade.Grade;
import hu.unideb.inf.NeptunPro.domain.repo.EventSourceGradeRepository;
import hu.unideb.inf.NeptunPro.domain.repo.GradeRepository;
import hu.unideb.inf.NeptunPro.domain.repo.UserRepository;
import hu.unideb.inf.NeptunPro.service.GradeService;
import hu.unideb.inf.NeptunPro.util.Utils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static hu.unideb.inf.NeptunPro.util.Utils.logApi;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/grade")
public class GradeController {

    private final ObjectMapper mapper;

    private final GradeRepository gradeRepository;
    private final EventSourceGradeRepository eventSourceGradeRepository;
    private final UserRepository userRepository;

    private final GradeService gradeService;

    @GetMapping("")
    public ResponseEntity<?> getAllGrades(final Principal principal) {
        logApi(HttpMethod.GET, principal, "getAllGrades");
        var lst = gradeRepository.findAll();
        return ResponseEntity.ok(lst);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGrade(
            @PathVariable final Long id,
            final Principal principal
    ) {
        logApi(HttpMethod.GET, principal, String.format("getGrade [%d]", id));
        var grade = gradeRepository.findById(id).orElse(null);

        return (grade == null) ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(grade);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<?> getGradeByStudent(
            @PathVariable final Long studentId,
            final Principal principal
    ) {
        logApi(HttpMethod.GET, principal, String.format("getGradeByStudent [%d]", studentId));
        var lst = gradeRepository.findAllByStudentId(studentId);
        return lst.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(lst);
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<?> getGradeEvents(
            @PathVariable final Long id,
            final Principal principal
    ) {
        logApi(HttpMethod.GET, principal, String.format("getGradeEvents [%d]", id));
        var lst = eventSourceGradeRepository.findAllByGradeId(id);
        return lst.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(lst);
    }

    @PostMapping("")
    public ResponseEntity<?> createGrade(
            @Valid @RequestBody final Grade received,
            final Principal principal
    ) {
        logApi(HttpMethod.POST, principal, "createGrade");
        var json = mapper.createObjectNode();

        try {
            var user = userRepository.findByEmail(principal.getName()).orElse(null);
            var savedGrade = gradeService.save(Utils.HTTP_CREATE, received, user);

            return new ResponseEntity<>(savedGrade, HttpStatus.CREATED);

        } catch (Exception e) {
            json.put("error", e.getMessage());
        }

        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGrade(
            @Valid @RequestBody final Grade received,
            @PathVariable final Long id,
            final Principal principal
    ) {
        logApi(HttpMethod.PUT, principal, String.format("updateGrade [%d]", id));
        var json = mapper.createObjectNode();

        var gradeInDb = gradeRepository.findById(id).orElse(null);
        var user = userRepository.findByEmail(principal.getName()).orElse(null);

        if (gradeInDb != null && user != null) {
            try {

                gradeInDb.setStudentId(received.getStudentId());
                gradeInDb.setCourseId(received.getCourseId());
                gradeInDb.setGrade(received.getGrade());

                var updated = gradeService.save(Utils.HTTP_UPDATE, gradeInDb, user);

                return ResponseEntity.ok(updated);
            } catch (Exception e) {
                json.put("error", e.getMessage());
            }
        }

        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGrade(
            @PathVariable final Long id,
            final Principal principal
    ) {
        logApi(HttpMethod.DELETE, principal, String.format("deleteGrade [%d]", id));
        if (gradeRepository.existsById(id)) {
            gradeService.delete(id);
        }

        return ResponseEntity.noContent().build();
    }

}
