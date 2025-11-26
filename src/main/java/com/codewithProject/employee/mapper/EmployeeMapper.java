package com.codewithProject.employee.mapper;

import com.codewithProject.employee.entity.Employee;
import com.codewithProject.employee.request.EmployeeRequest;
import com.codewithProject.employee.response.EmployeeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "phone", source = "request.phone")
    @Mapping(target = "department", source = "request.department")
    @Mapping(target = "user", source = "user")
    Employee toEntity(EmployeeRequest request, com.codewithProject.employee.entity.User user);

    EmployeeResponse toDTO(Employee employee);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "phone", source = "request.phone")
    @Mapping(target = "department", source = "request.department")
    void updateEntityFromRequest(EmployeeRequest request, @MappingTarget Employee employee);
}
