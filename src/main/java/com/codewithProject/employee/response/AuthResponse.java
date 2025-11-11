package com.codewithProject.employee.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Schema(description = "Réponse d'authentification avec token JWT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {


    @Schema(description = "Token JWT d'authentification", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Informations de l'utilisateur")
    private UserInfo user;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Schema(description = "Informations utilisateur")
    public static class UserInfo {
        @Schema(description = "ID de l'utilisateur", example = "123e4567-e89b-12d3-a456-426614174000")
        private UUID id;

        @Schema(description = "Email de l'utilisateur", example = "user@example.com")
        private String email;

        @Schema(description = "Nom de l'utilisateur", example = "Sofia Sofi")
        private String name;

        @Schema(description = "Rôle de l'utilisateur", example = "Admin")
        private String role;
    }


}
