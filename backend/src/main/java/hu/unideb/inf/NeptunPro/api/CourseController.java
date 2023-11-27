package hu.unideb.inf.NeptunPro.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import hu.unideb.inf.NeptunPro.domain.model.course.Course;
import hu.unideb.inf.NeptunPro.domain.repo.CourseRepository;
import hu.unideb.inf.NeptunPro.domain.repo.UserRepository;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static hu.unideb.inf.NeptunPro.util.Utils.logApi;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final ObjectMapper mapper;

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @GetMapping("")
    public ResponseEntity<?> getAllCourses(final Principal principal) {
        logApi(HttpMethod.GET, principal, "getAllCourses");
        var lst = courseRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return ResponseEntity.ok(lst);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchByName(
            @PathVariable("name") final String name,
            final Principal principal) {
        logApi(HttpMethod.GET, principal, String.format("searchByName [%s]", name));
        var arrayNode = mapper.createArrayNode();

        var courses = courseRepository.findTop10ByNameContains(name);
        for (var course : courses) {
            var node = arrayNode.addObject();
            node.put("id", course.getId());
            node.put("name", course.getName());
            node.put("type", course.getType().name());
        }

        return ResponseEntity.ok(arrayNode);
    }

    @PostMapping("")
    public ResponseEntity<?> createCourse(
            @Valid @RequestBody final Course received,
            final Principal principal) {

        logApi(HttpMethod.POST, principal, "createCourse");
        var json = mapper.createObjectNode();

        if(isCourseExists(received)) {
            json.put("error",
                    String.format("Course with name [%s] and type [%s] already exists",
                            received.getName(),
                            received.getType().toString())
            );
        }

        if(!userRepository.existsById(received.getTeacherId())) {
            json.put("error", String.format("Teacher with Id [%d] does NOT exist", received.getTeacherId()));
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }

        try {
            var saved = courseRepository.save(received);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            json.put("error", e.getMessage());
        }

        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(
            @Valid @RequestBody final Course received,
            @PathVariable final Long id,
            final Principal principal
    ) {
        logApi(HttpMethod.PUT, principal, String.format("updateCourse [%d]", id));

        var json = mapper.createObjectNode();

        var courseFromDb = courseRepository.findById(id).orElse(null);

        if(courseFromDb != null) {
            try {
                courseFromDb.setName(received.getName());
                courseFromDb.setType(received.getType());
                courseFromDb.setTeacherId(received.getTeacherId());

                var updated = courseRepository.saveAndFlush(courseFromDb);
                return ResponseEntity.ok(updated);
            } catch (Exception e) {
                json.put("error", e.getMessage());
            }
        }

        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(
            @PathVariable final Long id,
            final Principal principal
    ) {

        logApi(HttpMethod.DELETE, principal, String.format("deleteCourse [%d]", id));
        var json = mapper.createObjectNode();
        try {
            courseRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            json.put("error", e.getMessage());
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }
    }

    /////////////////////////////////////////////////////////////////////////
    // utils

    private boolean isCourseExists(final Course course) {
        return courseRepository.findByNameAndType(course.getName(), course.getType()) != null;
    }
}
