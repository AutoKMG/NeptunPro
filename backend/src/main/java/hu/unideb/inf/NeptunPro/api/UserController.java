package hu.unideb.inf.NeptunPro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.unideb.inf.NeptunPro.domain.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final ObjectMapper mapper;

    private final UserRepository userRepository;

    @GetMapping("")
    public ResponseEntity<?> getAllUsers() {
        var lst = userRepository.findAll(Sort.by(Sort.Direction.ASC, "firstname"));
        return ResponseEntity.ok(lst);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchByFullName(@PathVariable("name") final String name) {
        var arrayNode = mapper.createArrayNode();

        var users = userRepository.findTop10ByFullNameContains('%' + name + '%');
        for (var user : users) {
            var node = arrayNode.addObject();
            node.put("id", user.getId());
            node.put("fullName",
                    String.format("%s %s", user.getFirstname(), user.getLastname()));
        }

        return ResponseEntity.ok(arrayNode);
    }
}
