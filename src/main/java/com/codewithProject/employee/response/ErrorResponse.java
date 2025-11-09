package com.codewithProject.employee.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Réponse d'erreur")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    @Schema(description = "Code d'erreur HTTP", example = "404")
    private int status;

    @Schema(description = "Message d'erreur", example = "Employé introuvable")
    private String message;

    @Schema(description = "Timestamp", example = "2025-11-09T10:30:00")
    private LocalDateTime timestamp;

    public ErrorResponse (int status, String message ){
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
