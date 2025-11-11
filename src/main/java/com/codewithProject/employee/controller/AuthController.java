package com.codewithProject.employee.controller;

import com.codewithProject.employee.entity.User;
import com.codewithProject.employee.request.AuthRequest;
import com.codewithProject.employee.request.LoginRequest;
import com.codewithProject.employee.response.AuthResponse;
import com.codewithProject.employee.response.ErrorResponse;
import com.codewithProject.employee.response.MessageResponse;
import com.codewithProject.employee.security.TokenBlacklist;
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

import java.time.LocalDateTime;
import java.util.Map;
import com.codewithProject.employee.dto.UserResponseDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements IAuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenBlacklist tokenBlacklist;

    public AuthController(AuthService authService, JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService, TokenBlacklist tokenBlacklist) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
        this.tokenBlacklist = tokenBlacklist;
    }

    @Override
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRequest request) {
        try {
            User user = authService.register(
                    request.getEmail(),
                    request.getPassword(),
                    request.getName());


            var userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());
            String token = jwtUtil.generateToken(userDetails);

            AuthResponse response = AuthResponse.builder()
                    .token(token)
                    .user(AuthResponse.UserInfo.builder()
                            .id(user.getId())
                            .email(user.getEmail())
                            .name(user.getName())
                            .role(user.getRole())
                            .build())
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(400, e.getMessage(), LocalDateTime.now()));
        }
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            authService.authenticate(
                    request.getEmail(),
                    request.getPassword());

            var userDetails = customUserDetailsService.loadUserByUsername(request.getEmail());
            String token = jwtUtil.generateToken(userDetails);

            AuthResponse response = AuthResponse.builder()
                    .token(token)
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(401, "Email ou mot de passe incorrect", LocalDateTime.now()));
        }
    }

    @Override
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(name = "Authorization", required = false) String header) {
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            tokenBlacklist.add(token);
            return ResponseEntity.ok(new MessageResponse("Déconnexion réussie"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, "Aucun token fourni", LocalDateTime.now()));
        }
    }
}