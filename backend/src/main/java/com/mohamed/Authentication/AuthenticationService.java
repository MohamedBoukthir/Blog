package com.mohamed.Authentication;


import com.mohamed.DTO.UserDTOMapper;
import com.mohamed.Entities.User;
import com.mohamed.Repositories.UserRepository;
import com.mohamed.Utils.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // register
    public ResponseEntity<LoginResponse> register(RegisterRequest registerRequest) {
        // validation
        if(
                registerRequest.getEmail() == null
                        || registerRequest.getUsername() == null
                        || registerRequest.getFirstName() == null
                        || registerRequest.getLastName() == null
                        || registerRequest.getPassword() == null
        ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(LoginResponse.builder()
                            .error("Please fill in all fields")
                            .build()
                    );
        }
        try {
            if (userRepository.existsByUsername(registerRequest.getUsername())){
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(LoginResponse.builder()
                                .error("Username already exists")
                                .build()
                        );
            }
            User user = new User(
                    registerRequest.getUsername(),
                    registerRequest.getEmail(),
                    passwordEncoder.encode(registerRequest.getPassword()),
                    registerRequest.getFirstName(),
                    registerRequest.getLastName(),
                    registerRequest.getPhone(),
                    registerRequest.getAge()
                    );
            User userResponse = userRepository.save(user);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(LoginResponse.builder()
                            .message("User created successfully")
                            .userDto(new UserDTOMapper().apply(userResponse))
                            .build());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(LoginResponse.builder()
                            .error("An error occurred")
                            .build());
        }
    }

    // login
    public ResponseEntity<LoginResponse> login (LoginRequest loginRequest, HttpServletResponse httpServletResponse) {
        // validation
        if (loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(LoginResponse.builder()
                            .error("Please fill in all fields")
                            .build());
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
            User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found"));
            var accessToken = jwtService.generateToken(user);

            // set cookie
            int cookieExpiry = 60 * 60 * 24 * 7;
            ResponseCookie cookie = ResponseCookie.from("access_token", accessToken)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(cookieExpiry)
                    .sameSite("Lax")
                    .build();

            httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(LoginResponse.builder()
                            .message("Login successful")
                            .userDto(new UserDTOMapper().apply(user))
                            .build());
        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(LoginResponse.builder()
                            .error("Invalid credentials, Check your username and password")
                            .build()
                    );
        }
    }
}