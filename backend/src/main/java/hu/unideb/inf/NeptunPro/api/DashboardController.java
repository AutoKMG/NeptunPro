package hu.unideb.inf.NeptunPro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ObjectMapper mapper;

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @GetMapping("")
    public ResponseEntity<?> getDashboardInfo(final Principal principal) {
        logApi(HttpMethod.GET, principal, "getDashboardInfo");
        var json = mapper.createObjectNode();
        var studentCount = studentRepository.count();
        var courseCount = courseRepository.count();
        var userCount = userRepository.count();
        json.put("studentCount", studentCount);
        json.put("courseCount", courseCount);
        json.put("userCount", userCount);
        return ResponseEntity.ok(json);
    }
}
