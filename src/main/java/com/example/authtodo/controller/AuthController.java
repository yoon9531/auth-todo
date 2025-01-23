package com.example.authtodo.controller;

import com.example.authtodo.entity.JwtResponse;
import com.example.authtodo.entity.dto.RegisterRequestDTO;
import com.example.authtodo.entity.dto.UserCreateDTO;
import com.example.authtodo.security.JwtUtil;
import com.example.authtodo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Operation(summary = "Register a new user", description = "Register a new user with a username and password")
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        userService.registerUser(registerRequestDTO.getUsername(), registerRequestDTO.getPassword());
        return ResponseEntity.ok("User registered successfully");
    }

    @Operation(summary = "Login", description = "Login with a username and password")
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserCreateDTO user) {
        JwtResponse jwtResponse = userService.login(user.getUsername(), user.getPassword());

        return ResponseEntity.ok(jwtResponse);
    }

    @Data
    static class RegisterRequest {
        private String username;
        private String password;
    }

    @Data
    static class LoginRequest {
        private String username;
        private String password;
    }
}
