package ru.luttsev.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.luttsev.authservice.model.payload.request.SignInRequest;
import ru.luttsev.authservice.model.payload.request.SignUpRequest;
import ru.luttsev.authservice.model.payload.request.UpdateAccessTokenRequest;
import ru.luttsev.authservice.model.payload.response.TokenPairResponse;
import ru.luttsev.authservice.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-in")
    public TokenPairResponse signIn(@RequestBody SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public TokenPairResponse updateAccessToken(@RequestBody UpdateAccessTokenRequest updateAccessTokenRequest) {
        return authService.updateAccessToken(updateAccessTokenRequest);
    }
}
