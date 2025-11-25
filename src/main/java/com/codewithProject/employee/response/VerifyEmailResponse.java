package com.codewithProject.employee.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Réponse après vérification email")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyEmailResponse {

    @Schema(description = "Message de succes", example = "Succes, email est vérifié ")
    private String message;
}
