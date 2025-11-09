package com.codewithProject.employee.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Réponse contenant les information d'un employé")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {

    @Schema(description = "ID de l'employé", example = "1")
    private Long id;

    @Schema(description = "Nom de l'employé", example = "Louiza")
    private String name;

    @Schema(description = "Email de l'employé", example = "louiza@example.com")
    private String email;

    @Schema(description = "Numéro de téléphone", example = "0555555555")
    private String phone;

    @Schema(description = "Département", example = "IT")
    private String department;


    @Schema(description = "Date de création", example = "2025-11-09T10:30:00")
    private LocalDateTime createdAt;
}
