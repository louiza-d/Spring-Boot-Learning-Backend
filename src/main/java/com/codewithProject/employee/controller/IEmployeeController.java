package com.codewithProject.employee.controller;


import com.codewithProject.employee.entity.Employee;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "Employee", description = "API pour gérer les employés")
public interface IEmployeeController {

    @Operation(summary = "Créer un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "employé créé avec succés"),
            @ApiResponse(responseCode = "400", description = "requete invalide")
    })
   Employee postEmployee(@RequestBody Employee employee);

    @Operation(summary = "Obe=tenir la list de tous les employés")
    @ApiResponses({
           @ApiResponse(responseCode = "200", description =  "Liste recuperée avec succés ")
    })
    List<Employee> getAllEmployees();

    @Operation(summary = "Supprimer un employé par ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employé supprimé"),
            @ApiResponse(responseCode = "404", description = "Employé introuvable")
    })
    ResponseEntity<?> deleteEmployee(@PathVariable long id);

    @Operation(summary = "Obtenir un employé par ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employé trouvé"),
            @ApiResponse(responseCode = "404", description = "Employé introuvable")
    })
    ResponseEntity<?> getEmployeeById(@PathVariable long id);

    @Operation(summary = "Mettre à jour un employé existant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employé mis à jour"),
            @ApiResponse(responseCode = "400", description = "Requête invalide")
    })
    ResponseEntity<?> updateEmployee(@PathVariable long id, Employee employee);
}
