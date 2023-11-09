package hu.unideb.inf.NeptunPro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import hu.unideb.inf.NeptunPro.domain.model.course.Course;
import hu.unideb.inf.NeptunPro.domain.repo.CourseRepository;
import hu.unideb.inf.NeptunPro.domain.repo.UserRepository;
import hu.unideb.inf.NeptunPro.service.CourseService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final ObjectMapper mapper;

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    private final CourseService courseService;

    @GetMapping("")
    public ResponseEntity<?> getAllCourses() {
        var lst = courseRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return ResponseEntity.ok(lst);
    }

    @PostMapping("")
    public ResponseEntity<?> createCourse(@Valid @RequestBody final Course received) {
        // TODO: add auth admin check
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
            var saved = courseService.save(received);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            json.put("error", e.getMessage());
        }

        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(
            @Valid @RequestBody final Course received,
            @PathVariable final Long id
    ) {
        // TODO: add auth admin check
        var json = mapper.createObjectNode();

        var courseFromDb = courseRepository.findById(id).orElse(null);

        if(courseFromDb != null) {
            try {
                courseFromDb.setName(received.getName());
                courseFromDb.setType(received.getType());
                courseFromDb.setTeacherId(received.getTeacherId());

                var updated = courseService.save(courseFromDb);
                return ResponseEntity.ok(updated);
            } catch (Exception e) {
                json.put("error", e.getMessage());
            }
        }

        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable final Long id) {
        // TODO: add auth admin check
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
