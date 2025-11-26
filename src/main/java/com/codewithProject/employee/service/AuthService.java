package com.codewithProject.employee.service;

import com.codewithProject.employee.entity.User;
import com.codewithProject.employee.mapper.AuthMapper;
import com.codewithProject.employee.repository.UserRepository;
import com.codewithProject.employee.request.AuthRequest;
import com.codewithProject.employee.request.LoginRequest;
import com.codewithProject.employee.response.LoginSuccessResponse;
import com.codewithProject.employee.response.MessageResponse;
import com.codewithProject.employee.response.RegisterResponse;
import com.codewithProject.employee.response.VerifyEmailResponse;
import com.codewithProject.employee.security.JwtUtil;
import com.codewithProject.employee.security.TokenBlacklist;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private final TokenBlacklist tokenBlacklist;
    private final AuthMapper authMapper;

    public RegisterResponse registerUser(AuthRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        String encoded = passwordEncoder.encode(request.getPassword());
        String verificationToken = UUID.randomUUID().toString();
        ZonedDateTime expiration = ZonedDateTime.now().plusHours(24);

        User user = authMapper.toUser(request, encoded, verificationToken, expiration);

        userRepository.save(user);

        emailService.sendVerificationEmail(user.getEmail(), verificationToken);
        return authMapper.toRegisterResponse(user);
    }

    //
    public LoginSuccessResponse loginUser(LoginRequest request) {

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Veuillez d'abord vérifier votre adress e-mail avant de vous connecter.");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        String token = jwtUtil.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(), user.getPassword(), List.of()));

        return authMapper.toLoginResponse(user, token);
    }

    // LOGOUT
    public MessageResponse logoutUser(String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Aucun token fourni");
        }

        String token = header.substring(7);
        tokenBlacklist.add(token);

        return new MessageResponse("Déconnexion réussie");
    }

    //
    public VerifyEmailResponse verifyEmailToken(String token) {
        var user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalide"));

        if (user.getTokenExpiration().isBefore(ZonedDateTime.now())) {
            throw new RuntimeException("Token expiré");
        }

        user.setEnabled(true);
        user.setVerificationToken(null);
        user.setTokenExpiration(null);
        userRepository.save(user);

        return authMapper.toVerifyEmailResponse(user);
    }
}