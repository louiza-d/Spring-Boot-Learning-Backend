package com.codewithProject.employee.controller;

import com.codewithProject.employee.entity.User;
import com.codewithProject.employee.service.AuthService;
import com.codewithProject.employee.security.JwtUtil;
import com.codewithProject.employee.service.CustomUserDetailsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import com.codewithProject.employee.dto.UserResponseDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Data
    public static class RegisterRequest {
        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "Format d'email invalide")
        private String email;

        @NotBlank(message = "Le mot de passe est obligatoire")
        private String password;

        @NotBlank(message = "Le nom est obligatoire")
        private String name;
    }

    @Data
    public static class LoginRequest {
        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "Format d'email invalide")
        private String email;

        @NotBlank(message = "Le mot de passe est obligatoire")
        private String password;
    }

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthController(AuthService authService, JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            User user = authService.register(
                    request.getEmail(),
                    request.getPassword(),
                    request.getName());

            UserResponseDTO response = new UserResponseDTO(
                    user.getId(),
                    user.getEmail(),
                    user.getName(),
                    user.getRole());

            var userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
            String token = jwtUtil.generateToken(userDetails);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("token", token, "user", response));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            authService.authenticate(
                    request.getEmail(),
                    request.getPassword());

            var userDetails = customUserDetailsService.loadUserByUsername(request.getEmail());
            String token = jwtUtil.generateToken(userDetails);
            
            return ResponseEntity.ok()
                    .body(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Email ou mot de passe incorrect"));
        }
    }
}