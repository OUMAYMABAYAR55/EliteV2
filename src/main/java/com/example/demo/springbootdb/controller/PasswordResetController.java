package com.example.demo.springbootdb.controller;

import com.example.demo.springbootdb.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
@CrossOrigin
public class PasswordResetController {

    @Autowired
    private PasswordResetService resetService;

    @PostMapping("/forgot")
    public String forgotPassword(@RequestParam String email) {
        resetService.createPasswordResetToken(email, "http://localhost:4200");
        return "Un email a été envoyé avec un lien de réinitialisation.";
    }

    @PostMapping("/reset")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        boolean success = resetService.resetPassword(token, newPassword);
        return success ? "Mot de passe mis à jour." : "Lien expiré ou invalide.";
    }

    @GetMapping("/validate")
    public boolean validateToken(@RequestParam String token) {
        return resetService.validateToken(token);
    }
}
