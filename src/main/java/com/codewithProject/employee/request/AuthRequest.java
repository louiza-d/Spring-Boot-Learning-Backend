package com.codewithProject.employee.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Reaquete d'inscription")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @Schema(description = "Adress email", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private  String email;

    @Schema(description = "Mot de passe", example = "Password123!", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;

    @Schema(description = "Nom complet", example = "Sofia sofi", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Le nom est obligatoire")
    private String name;
}
