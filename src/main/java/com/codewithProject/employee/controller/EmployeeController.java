package com.codewithProject.employee.controller;

import com.codewithProject.employee.request.EmployeeRequest;
import com.codewithProject.employee.response.EmployeeResponse;
import com.codewithProject.employee.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class EmployeeController implements IEmployeeController {

    private final EmployeeService employeeService;

    @Override
    @PostMapping("/employee")
    @Operation(summary = "Créer un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Employé créé"),
            @ApiResponse(responseCode = "400", description = "Erreur de validation"),
            @ApiResponse(responseCode = "401", description = "Non authentifié")
    })
    public EmployeeResponse postEmployee(@RequestBody @Valid EmployeeRequest request) {
        return employeeService.postEmployee(request);
    }

    @Override
    @GetMapping("/employees")
    @Operation(summary = "Lister tous les employés")
    @ApiResponse(responseCode = "200", description = "Liste d'employés")
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @Override
    @DeleteMapping("/employee/{id}")
    @Operation(summary = "Supprimer un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employé supprimé"),
            @ApiResponse(responseCode = "404", description = "Employé non trouvé")
    })
    public ResponseEntity<?> deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee with ID " + id + " deleted successfully");
    }

    @Override
    @GetMapping("/employee/{id}")
    @Operation(summary = "Récupérer un employé par ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employé trouvé"),
            @ApiResponse(responseCode = "404", description = "Employé non trouvé")
    })
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable long id) {
        EmployeeResponse employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @PatchMapping("/employee/{id}")
    @Operation(summary = "Mettre à jour un employé")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employé mis à jour"),
            @ApiResponse(responseCode = "400", description = "Erreur de validation"),
            @ApiResponse(responseCode = "404", description = "Employé non trouvé")
    })
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable long id,
            @RequestBody @Valid EmployeeRequest request) {
        EmployeeResponse updatedEmployee = employeeService.updateEmployee(id, request);
        return ResponseEntity.ok(updatedEmployee);
    }
}
