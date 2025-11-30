package com.codewithProject.employee.service;

import com.codewithProject.employee.entity.Employee;
import com.codewithProject.employee.entity.User;
import com.codewithProject.employee.mapper.EmployeeMapper;
import com.codewithProject.employee.repository.EmployeeRepository;
import com.codewithProject.employee.request.EmployeeRequest;
import com.codewithProject.employee.response.EmployeeResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final CurrentUserService currentUserService;
    private final EmployeeMapper employeeMapper;

    public EmployeeResponse postEmployee(EmployeeRequest request) {
        User currentUser = currentUserService.getCurrentUser();

        if (employeeRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Employee with email " + request.getEmail() + " already exists");
        }

        if (employeeRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new IllegalArgumentException("Employee with phone " + request.getPhone() + " already exists");
        }

        Employee employee = employeeMapper.toEntity(request, currentUser);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toDTO(savedEmployee);
    }

    public List<EmployeeResponse> getAllEmployees() {
        User currentUser = currentUserService.getCurrentUser();
        List<Employee> employees = employeeRepository.findByUser(currentUser);
        return employees.stream()
                .map(employeeMapper::toDTO)
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
        return employeeMapper.toDTO(employee);
    }

    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with ID " + id + " not found"));

        
        if (request.getEmail() != null && !request.getEmail().equalsIgnoreCase(existingEmployee.getEmail())) {
            employeeRepository.findByEmail(request.getEmail()).ifPresent(e -> {
                if (e.getId() != existingEmployee.getId()) {
                    throw new IllegalArgumentException("Employee with email " + request.getEmail() + " already exists");
                }
            });
        }

        
        if (request.getPhone() != null && !request.getPhone().equalsIgnoreCase(existingEmployee.getPhone())) {
            employeeRepository.findByPhone(request.getPhone()).ifPresent(e -> {
                if (e.getId() != existingEmployee.getId()) {
                    throw new IllegalArgumentException("Employee with phone " + request.getPhone() + " already exists");
                }
            });
        }
        employeeMapper.updateEntityFromRequest(request, existingEmployee);
        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        logger.info("Updated employee {} (id={})", updatedEmployee.getEmail(), updatedEmployee.getId());
        return employeeMapper.toDTO(updatedEmployee);
    }
}