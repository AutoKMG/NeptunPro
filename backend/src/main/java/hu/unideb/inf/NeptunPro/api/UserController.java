package hu.unideb.inf.NeptunPro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.unideb.inf.NeptunPro.domain.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static hu.unideb.inf.NeptunPro.util.Utils.logApi;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final ObjectMapper mapper;

    private final UserRepository userRepository;

    @GetMapping("")
    public ResponseEntity<?> getAllUsers(final Principal principal) {
        logApi(HttpMethod.GET, principal, "getAllUsers");
        var lst = userRepository.findAll(Sort.by(Sort.Direction.ASC, "firstname"));
        return ResponseEntity.ok(lst);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchByFullName(
            @PathVariable("name") final String name,
            final Principal principal
    ) {
        logApi(HttpMethod.GET, principal, String.format("searchByFullName [%s]", name));

        var users = userRepository.findTop10ByFullNameContains('%' + name + '%');

        return ResponseEntity.ok(users);
    }
}
