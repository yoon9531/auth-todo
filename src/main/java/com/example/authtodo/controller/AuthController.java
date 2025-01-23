package com.example.authtodo.controller;

import com.example.authtodo.entity.JwtResponse;
import com.example.authtodo.security.JwtUtil;
import com.example.authtodo.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Map<String, String> user) {
        userService.registerUser(user.get("username"), user.get("password"));
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody Map<String, String> user) {
        JwtResponse jwtResponse = userService.login(user.get("username"), user.get("password"));

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
