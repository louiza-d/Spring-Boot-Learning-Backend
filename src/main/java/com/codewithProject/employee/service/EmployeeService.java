package com.codewithProject.employee.service;


import com.codewithProject.employee.entity.Employee;
import com.codewithProject.employee.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Employee postEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public void deleteEmployee(long id){
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee with ID "+ id + "not found");
        }
        employeeRepository.deleteById(id);
    }

    public Employee getEmployeeById(long id) {
        return employeeRepository.findById(id).orElse(null);
    }
}
