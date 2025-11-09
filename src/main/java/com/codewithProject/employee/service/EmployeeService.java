package com.codewithProject.employee.service;

import com.codewithProject.employee.entity.Employee;
import com.codewithProject.employee.entity.User;
import com.codewithProject.employee.repository.EmployeeRepository;
import com.codewithProject.employee.request.EmployeeRequest;
import com.codewithProject.employee.response.EmployeeResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final CurrentUserService currentUserService;

    public EmployeeResponse postEmployee(EmployeeRequest request) {
        User currentUser = currentUserService.getCurrentUser();


        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDepartment(request.getDepartment());
        employee.setUser(currentUser);

        Employee savedEmployee = employeeRepository.save(employee);

        return toResponse(savedEmployee);
    }

    public List<EmployeeResponse> getAllEmployees() {
        User currentUser = currentUserService.getCurrentUser();
        List<Employee> employees = employeeRepository.findByUser(currentUser);

        return employees.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteEmployee(long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee with ID " + id + " not found");
        }
        employeeRepository.deleteById(id);
    }

    public EmployeeResponse getEmployeeById(long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with ID " + id + " not found"));
        return toResponse(employee);
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with ID " + id + " not found"));

        if (request.getName() != null && !request.getName().isBlank()) {
            existingEmployee.setName(request.getName());
        }
        if (request.getEmail() != null && !request.getEmail().isBlank()) {
            existingEmployee.setEmail(request.getEmail());
        }
        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            existingEmployee.setPhone(request.getPhone());
        }
        if (request.getDepartment() != null && !request.getDepartment().isBlank()) {
            existingEmployee.setDepartment(request.getDepartment());
        }

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return toResponse(updatedEmployee);
    }

    private EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .name(employee.getName())
                .email(employee.getEmail())
                .phone(employee.getPhone())
                .department(employee.getDepartment())
                .createdAt(employee.getCreatedAt())
                .build();
    }
}