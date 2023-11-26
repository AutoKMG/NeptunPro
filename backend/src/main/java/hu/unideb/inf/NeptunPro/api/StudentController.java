package hu.unideb.inf.NeptunPro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.unideb.inf.NeptunPro.domain.model.student.Student;
import hu.unideb.inf.NeptunPro.domain.repo.EventSourceStudentRepository;
import hu.unideb.inf.NeptunPro.domain.repo.StudentRepository;
import hu.unideb.inf.NeptunPro.domain.repo.UserRepository;
import hu.unideb.inf.NeptunPro.service.StudentService;
import hu.unideb.inf.NeptunPro.util.Utils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final ObjectMapper mapper;

    private final StudentRepository studentRepository;
    private final EventSourceStudentRepository eventSourceStudentRepository;
    private final UserRepository userRepository;

    private final StudentService studentService;

    @GetMapping("")
    public ResponseEntity<?> getAllStudents() {
        var lst = studentRepository.findAll();
        return ResponseEntity.ok(lst);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchByFullName(@PathVariable("name") final String name) {
        var arrayNode = mapper.createArrayNode();

        var students = studentRepository.findTop10ByFullNameContains('%' + name + '%');
        for (var student : students) {
            var node = arrayNode.addObject();
            node.put("id", student.getId());
            node.put("fullName",
                    String.format("%s %s", student.getFirstname(), student.getLastname()));
            node.put("programId", student.getProgramId());
        }

        return ResponseEntity.ok(arrayNode);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable final Long id) {
        var student = studentRepository.findById(id).orElse(null);

        return (student == null) ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(student);
    }

    @GetMapping("/{id}/events")
    public ResponseEntity<?> getStudentEvents(@PathVariable final Long id) {
        var lst = eventSourceStudentRepository.findAllByStudentId(id);
        return lst.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(lst);
    }

    @PostMapping("")
    public ResponseEntity<?> createStudent(
            @Valid @RequestBody final Student received,
            final Principal principal
    ) {
        var json = mapper.createObjectNode();

        try {
            var user = userRepository.findByEmail(principal.getName()).orElse(null);
            var savedStudent = studentService.save(Utils.HTTP_CREATE, received, user);

            return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
        } catch (Exception e) {
            json.put("error", e.getMessage());
        }

        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(
            @Valid @RequestBody final Student received,
            @PathVariable final Long id,
            final Principal principal
    ) {
        var json = mapper.createObjectNode();

        var studentInDb = studentRepository.findById(id).orElse(null);
        var user = userRepository.findByEmail(principal.getName()).orElse(null);

        if (studentInDb != null && user != null) {
            try {

                studentInDb.setFirstname(received.getFirstname());
                studentInDb.setLastname(received.getLastname());
                studentInDb.setProgramId(received.getProgramId());
                studentInDb.setGpa(received.getGpa());

                var updated = studentService.save(Utils.HTTP_UPDATE, studentInDb, user);

                return ResponseEntity.ok(updated);
            } catch (Exception e) {
                json.put("error", e.getMessage());
            }
        }

        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable final Long id) {
        if(studentRepository.existsById(id)) {
            studentService.delete(id);
        }

        return ResponseEntity.noContent().build();
    }

}
