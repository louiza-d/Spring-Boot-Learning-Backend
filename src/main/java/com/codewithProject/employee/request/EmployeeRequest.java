package com.codewithProject.employee.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Requête pour créer ou mettre à jour un employé")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    @Schema(description = "Nom de l'employé", example = "louiza", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Le nom est obligatoire")
    private String name;

    @Schema(description = "Email de l'employé", example = "email1@example.com", requiredMode =  Schema.RequiredMode.REQUIRED)
    @Email(message = "Email invalide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    @Schema(description = "Numéro de téléphone de l'employé", example = "0555555555", requiredMode =  Schema.RequiredMode.REQUIRED)
    @Pattern(regexp = "^(0[5-7])[0-9]{8}$", message = "Le numéro de téléphone doit être un numéro algérien valide (ex: 0555555555)")
    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    private String phone;

    @Schema(description = "Département", example = "IT")
    private String department;



}
