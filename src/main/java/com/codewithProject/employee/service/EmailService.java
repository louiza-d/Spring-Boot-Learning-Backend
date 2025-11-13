package com.codewithProject.employee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender  mailSender;

    public void sendVerificationEmail(String to, String token){
        String subject = "Confirmez votre inscription";
        String verificationUrl = "http://localhost:3000/verify?token="+token;
        String message = """
                Bonjour,
                Merci de vous etre inscrit !
                Cliquez sur le lien suivant pour activer votre compte :
                
                %s
                
                ce lien expirera dans 24 heures.
                """.formatted(verificationUrl);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }

}
