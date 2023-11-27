package hu.unideb.inf.NeptunPro.api;

import hu.unideb.inf.NeptunPro.domain.repo.CourseRepository;
import hu.unideb.inf.NeptunPro.domain.repo.StudentRepository;
import hu.unideb.inf.NeptunPro.domain.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static hu.unideb.inf.NeptunPro.util.Utils.logApi;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @GetMapping("/student")
    public ResponseEntity<?> getStudentCount(final Principal principal) {
        logApi(HttpMethod.GET, principal, "getStudentCount");
        return ResponseEntity.ok(studentRepository.count());
    }

    @GetMapping("/course")
    public ResponseEntity<?> getCourseCount(final Principal principal) {
        logApi(HttpMethod.GET, principal, "getCourseCount");
        return ResponseEntity.ok(courseRepository.count());
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserCount(final Principal principal) {
        logApi(HttpMethod.GET, principal, "getUserCount");
        return ResponseEntity.ok(userRepository.count());
    }


}
