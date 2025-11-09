package com.codewithProject.employee.controller;

import com.codewithProject.employee.request.EmployeeRequest;
import com.codewithProject.employee.response.EmployeeResponse;
import com.codewithProject.employee.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Employee", description = "API pour gérer les employés")
public interface IEmployeeController {

    @Operation(
            summary = "Créer un employé",
            description = "Crée un nouveau employé dans le système"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employé créé avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requête invalide - erreur de validation",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    EmployeeResponse postEmployee(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Informations de l'employé à créer",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EmployeeRequest.class))
            )
            @RequestBody @Valid EmployeeRequest request
    );

    @Operation(
            summary = "Obtenir la liste de tous les employés",
            description = "Récupère tous les employés enregistrés dans le système"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Liste récupérée avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EmployeeResponse.class))
                    )
            )
    })
    List<EmployeeResponse> getAllEmployees();

    @Operation(
            summary = "Supprimer un employé par ID",
            description = "Supprime définitivement un employé du système"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employé supprimé avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "Employee with ID 1 deleted successfully")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employé introuvable",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    ResponseEntity<?> deleteEmployee(
            @Parameter(description = "ID de l'employé à supprimer", required = true, example = "1")
            @PathVariable long id
    );

    @Operation(
            summary = "Obtenir un employé par ID",
            description = "Récupère les informations détaillées d'un employé"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employé trouvé",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employé introuvable",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    ResponseEntity<?> getEmployeeById(
            @Parameter(description = "ID de l'employé", required = true, example = "1")
            @PathVariable long id
    );

    @Operation(
            summary = "Mettre à jour un employé existant",
            description = "Met à jour partiellement les informations d'un employé (PATCH)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Employé mis à jour avec succès",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requête invalide",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employé introuvable",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    ResponseEntity<?> updateEmployee(
            @Parameter(description = "ID de l'employé à mettre à jour", required = true, example = "1")
            @PathVariable long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nouvelles informations de l'employé",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EmployeeRequest.class))
            )
            @RequestBody @Valid EmployeeRequest request
    );
}