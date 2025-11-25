package com.codewithProject.employee.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Réponse apres connexion réussie")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginSuccessResponse {

    @Schema(description = "Message de succes", example = "Succes")
    private String message;

    @Schema(description = "access Token", example ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." )
    private String accessToken;
}
