package com.codewithProject.employee.mapper;

import com.codewithProject.employee.entity.User;
import com.codewithProject.employee.request.AuthRequest;
import com.codewithProject.employee.response.LoginSuccessResponse;
import com.codewithProject.employee.response.RegisterResponse;
import com.codewithProject.employee.response.VerifyEmailResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    // Request -> Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", constant = "false")
    @Mapping(target = "verificationToken", ignore = true)
    @Mapping(target = "tokenExpiration", ignore = true)
    User toUser(AuthRequest request, String encodedPassword, String verificationToken, java.time.ZonedDateTime tokenExpiration);

    // User -> RegisterResponse
    @Mapping(target = "message", constant = "Un email de vérification vous a été envoyé")
    @Mapping(target = "name", source = "name")
    RegisterResponse toRegisterResponse(User user);

    // User + token -> LoginSuccessResponse
    @Mapping(target = "message", constant = "Succes")
    @Mapping(target = "accessToken", source = "token")
    LoginSuccessResponse toLoginResponse(User user, String token);

    // VerifyEmailResponse
    @Mapping(target = "message", constant = "Compte vérifié avec succes")
    VerifyEmailResponse toVerifyEmailResponse(User user);
}
