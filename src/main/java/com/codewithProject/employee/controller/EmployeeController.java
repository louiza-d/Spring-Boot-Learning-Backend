package com.codewithProject.employee.controller;

import com.codewithProject.employee.request.EmployeeRequest;
import com.codewithProject.employee.response.EmployeeResponse;
import com.codewithProject.employee.response.ErrorResponse;
import com.codewithProject.employee.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmployeeController implements IEmployeeController {

    private final EmployeeService employeeService;

    @Override
    @PostMapping("/employee")
    public EmployeeResponse postEmployee(@RequestBody @Valid EmployeeRequest request) {
        return employeeService.postEmployee(request);
    }

    @Override
    @GetMapping("/employees")
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @Override
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Employee with ID " + id + " deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, e.getMessage(), LocalDateTime.now()));
        }
    }

    @Override
    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable long id) {
        try {
            EmployeeResponse employee = employeeService.getEmployeeById(id);
            return ResponseEntity.ok(employee);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, e.getMessage(), LocalDateTime.now()));
        }
    }

    @PatchMapping("/employee/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable long id, @RequestBody @Valid  EmployeeRequest request) {
        try {
            EmployeeResponse updatedEmployee = employeeService.updateEmployee(id, request);
            return ResponseEntity.ok(updatedEmployee);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(404, e.getMessage(), LocalDateTime.now()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(400, e.getMessage(), LocalDateTime.now()));
        }
} }
