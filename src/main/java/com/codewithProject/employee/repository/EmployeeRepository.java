package com.codewithProject.employee.repository;

import com.codewithProject.employee.entity.Employee;
import com.codewithProject.employee.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByUser(User user);
}
