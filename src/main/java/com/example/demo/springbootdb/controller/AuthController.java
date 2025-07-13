package com.example.demo.springbootdb.controller;

import com.example.demo.springbootdb.Models.Utilisateur;
import com.example.demo.springbootdb.security.JwtService;
import com.example.demo.springbootdb.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Utilisateur utilisateur) {
        try {
            String result = authService.register(utilisateur);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur inscription : " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Email et mot de passe requis");
        }

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            if (auth.isAuthenticated()) {
                Utilisateur utilisateur = authService.findByEmail(email);
                if (utilisateur == null) {
                    return ResponseEntity.status(401).body("Utilisateur introuvable");
                }

                UserDetails userDetails = User.withUsername(utilisateur.getEmail())
                        .password(utilisateur.getPassword())
                        .roles(utilisateur.getRole().name())
                        .build();

                String token = jwtService.generateToken(userDetails, utilisateur.getRole());

                return ResponseEntity.ok(Map.of(
                        "token", token,
                        "role", utilisateur.getRole().name()
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Identifiants incorrects");
        }

        return ResponseEntity.status(401).body("Erreur d'authentification");
    }
}
