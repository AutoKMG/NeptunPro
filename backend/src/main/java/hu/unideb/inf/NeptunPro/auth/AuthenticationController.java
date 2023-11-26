package hu.unideb.inf.NeptunPro.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static hu.unideb.inf.NeptunPro.util.Utils.logApi;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody final RegisterRequest request,
            final Principal principal
    ) {
        logApi(HttpMethod.POST, principal, "register");
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody final AuthenticationRequest request,
            final Principal principal
    ) {
        logApi(HttpMethod.POST, principal, "authenticate");
        return ResponseEntity.ok(service.authenticate(request));
    }
}
