package com.codewithProject.employee.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Reponse apres inscription")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponse {

    @Schema(description = "Message de confirmation", example = "Un email de verification vous a été envoyé")
    private String message;

    @Schema(description = "Nom complet de l'utilisateur", example = "LOUIZ Louiza")
    private String name;
}
