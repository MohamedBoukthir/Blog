package com.mohamed.Controllers;

import com.mohamed.Authentication.AuthenticationService;
import com.mohamed.Authentication.LoginRequest;
import com.mohamed.Authentication.LoginResponse;
import com.mohamed.Authentication.RegisterRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public LoginResponse register(@RequestBody RegisterRequest registerRequest) {
        return authenticationService.register(registerRequest).getBody();
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        return authenticationService.login(loginRequest, response).getBody();
    }

}


