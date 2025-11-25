package com.codewithProject.employee.controller;

import com.codewithProject.employee.request.AuthRequest;
import com.codewithProject.employee.request.LoginRequest;
import com.codewithProject.employee.response.*;
import com.codewithProject.employee.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name= "Authentification", description = "API d'authentification et gestion des sessions")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/register")
    @Operation(
            summary = "Inscription d'un nouvel utilisateur",
            description = "Crée un compte utilisateur et envoie un email de vérification"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Inscription réussie"),
            @ApiResponse(responseCode = "400", description = "Erreur de validation")
    })
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody AuthRequest request) {
       RegisterResponse response = authService.registerUser(request);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PostMapping("/login")
    @Operation(
            summary = "Connexion utilisateur",
            description = "Authentifie un utilisateur et retourne un token JWT"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Connexion réussie"),
            @ApiResponse(responseCode = "401", description = "Identifiants invalides")
    })
    public ResponseEntity<LoginSuccessResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginSuccessResponse response = authService.loginUser(request);
            return ResponseEntity.ok(response);

    }


    @PostMapping("/logout")
    @Operation(
            summary = "Déconnexion utilisateur",
            description = "Invalide le token JWT"
    )
    public ResponseEntity<MessageResponse> logout(@RequestHeader(name = "Authorization", required = false) String header) {
        MessageResponse response = authService.logoutUser(header);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify")
    @Operation(
            summary = "Vérification d'email",
            description = "Active le compte si le token est valide"
    )
    public ResponseEntity<VerifyEmailResponse> verifyAccount(@RequestParam("token") String token) {
        VerifyEmailResponse response = authService.verifyEmailToken(token);
        return ResponseEntity.ok(response);
    }
}