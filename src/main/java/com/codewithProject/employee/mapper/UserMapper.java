package com.codewithProject.employee.mapper;

import com.codewithProject.employee.entity.User;
import com.codewithProject.employee.response.AuthResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    AuthResponse.UserInfo toUserInfo(User user);
}
