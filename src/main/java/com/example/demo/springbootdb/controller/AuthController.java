package com.example.demo.springbootdb.controller;

import com.example.demo.springbootdb.Models.Utilisateur;
import com.example.demo.springbootdb.service.AuthService;
import com.example.demo.springbootdb.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    public String register(@Valid @RequestBody Utilisateur utilisateur) {
        return authService.register(utilisateur);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            if (auth.isAuthenticated()) {
                String token = jwtService.generateToken(email);
                return ResponseEntity.ok(Map.of("token", token));
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Identifiants incorrects ❌");
        }

        return ResponseEntity.status(401).body("Erreur d'authentification");
    }
}
