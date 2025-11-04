package com.codewithProject.employee.controller;

import com.codewithProject.employee.entity.User;
import com.codewithProject.employee.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import com.codewithProject.employee.dto.UserResponseDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        try {
            User user = authService.register(
                    request.get("email"),
                    request.get("password"),
                    request.get("name"));

            UserResponseDTO response = new UserResponseDTO(
                    user.getId(),
                    user.getEmail(),
                    user.getName(),
                    user.getRole());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        Authentication authentication = authService.authenticate(
                request.get("email"),
                request.get("password"));

        return ResponseEntity.ok("Authentication successful");
    }
}