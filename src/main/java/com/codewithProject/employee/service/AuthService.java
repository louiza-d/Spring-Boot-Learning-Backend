package com.codewithProject.employee.service;

import com.codewithProject.employee.entity.User;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
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


    public RegisterResponse registerUser(AuthRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        user.setEnabled(false);

        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setTokenExpiration(ZonedDateTime.now().plusHours(24));

         userRepository.save(user);

        emailService.sendVerificationEmail(user.getEmail(), token);
        return RegisterResponse.builder()
                .message("Un email de verification vous à été envoyé")
                .name(user.getName())
                .build();
    }

    //
    public LoginSuccessResponse loginUser(LoginRequest request) {

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Veuillez d'abord vérifier votre adress e-mail avant de vous connecter.");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );

        String token = jwtUtil.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getEmail(), user.getPassword(), List.of()
                )
        );

        return LoginSuccessResponse.builder()
                .message("Succes")
                .accessToken(token).build();
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
    public VerifyEmailResponse verifyEmailToken (String token) {
        var user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalide"));

        if (user.getTokenExpiration().isBefore(ZonedDateTime.now())) {
            throw  new RuntimeException("Token expiré");
        }

        user.setEnabled(true);
        user.setVerificationToken(null);
        user.setTokenExpiration(null);
        userRepository.save(user);

        return new VerifyEmailResponse("Compte vérifié avec succes");
    }
}