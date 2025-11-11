package com.codewithProject.employee.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Réponse avec message")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {


    @Schema(description = "Message de réponse", example = "Déconnexion réussie")
    private String message;
}
