package com.example.demo.springbootdb.service;

import com.example.demo.springbootdb.Models.PasswordResetToken;
import com.example.demo.springbootdb.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired private PasswordResetTokenRepository tokenRepo;
    @Autowired private EmailService emailService;
    @Autowired private UtilisateurService userService; // ton service user
    @Autowired private PasswordEncoder passwordEncoder;

    public void createPasswordResetToken(String email, String baseUrl) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setEmail(email);
        resetToken.setToken(token);
        resetToken.setExpirationDate(LocalDateTime.now().plusHours(1));
        tokenRepo.save(resetToken);

        String resetLink = baseUrl + "/reset-password?token=" + token;
        emailService.sendResetPasswordEmail(email, resetLink);
    }

    public boolean validateToken(String token) {
        Optional<PasswordResetToken> tokenOpt = tokenRepo.findByToken(token);
        return tokenOpt.isPresent() && tokenOpt.get().getExpirationDate().isAfter(LocalDateTime.now());
    }

    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> tokenOpt = tokenRepo.findByToken(token);
        if (tokenOpt.isPresent() && validateToken(token)) {
            String email = tokenOpt.get().getEmail();
            userService.updatePassword(email, passwordEncoder.encode(newPassword));
            tokenRepo.delete(tokenOpt.get());
            return true;
        }
        return false;
    }
}
