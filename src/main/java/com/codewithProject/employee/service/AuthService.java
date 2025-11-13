package com.codewithProject.employee.service;

import com.codewithProject.employee.entity.User;
import com.codewithProject.employee.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    public User register(String email, String password, String name) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setName(name);
        user.setEnabled(false);

        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setTokenExpiration(ZonedDateTime.now().plusHours(24));

         userRepository.save(user);

        emailService.sendVerificationEmail(user.getEmail(), token);
        return user;
    }

    public Authentication authenticate(String email, String password) {

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Veuillez d'abord vérifier votre adress e-mail avant de vous connecter.");
        }

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
    }

    public Optional<User> verifyUser(String token) {
        return userRepository.findByVerificationToken(token).map(user -> {
            if (user.getTokenExpiration().isBefore(ZonedDateTime.now())) {
                return null;
            }
            user.setEnabled(true);
            user.setVerificationToken(null);
            user.setTokenExpiration(null);
            userRepository.save(user);
            return user;
        });
    }
}