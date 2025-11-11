package com.codewithProject.employee.controller;

import com.codewithProject.employee.request.LoginRequest;
import com.codewithProject.employee.request.AuthRequest;
import com.codewithProject.employee.response.AuthResponse;
import com.codewithProject.employee.response.ErrorResponse;
import com.codewithProject.employee.response.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name= "Authentification", description = "API d'authentification et gestion des sessions")
public interface IAuthController {

    @Operation(
            summary = "Inscription d'un nouvel utilisateur",
            description = "Crée un nouveau compte utilisateur et retourne un token JWT"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Utilisateur créé avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erreur de validation ou utilisateur déjà existant",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    ResponseEntity<?> register(
            @RequestBody @Valid AuthRequest request
    );

    @Operation(
            summary = "Connexion d'un utilisateur",
            description = "Authentifie un utilisateur et retourne un token JWT"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Connexion réussie",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Email ou mot de passe incorrect",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    ResponseEntity<?> login(
            @RequestBody @Valid LoginRequest request
    );

    @Operation(
            summary = "Déconnexion de l'utilisateur",
            description = "Invalide le token JWT actuel en l'ajoutant à une liste noire",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Déconnexion réussie",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Token manquant ou invalide",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    ResponseEntity<?> logout(
            @Parameter(
                    description = "Token JWT au format: Bearer {token}",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            )
            @RequestHeader(name = "Authorization", required = false) String header
    );
}
