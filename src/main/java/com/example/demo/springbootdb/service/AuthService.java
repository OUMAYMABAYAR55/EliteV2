package com.example.demo.springbootdb.service;

import com.example.demo.springbootdb.Models.Role;
import com.example.demo.springbootdb.Models.Utilisateur;
import com.example.demo.springbootdb.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String register(Utilisateur utilisateur) {
        if (utilisateurRepository.existsByEmail(utilisateur.getEmail())) {
            return "Email déjà utilisé.";
        }

        // Encoder le mot de passe
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));

        // Définir un rôle par défaut si non fourni
        if (utilisateur.getRole() == null) {
            utilisateur.setRole(Role.MEMBRE); // Rôle par défaut
        }

        utilisateurRepository.save(utilisateur);
        return "Utilisateur enregistré avec succès.";
    }

    public boolean login(String email, String rawPassword) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(email);
        return utilisateurOpt.map(utilisateur ->
                passwordEncoder.matches(rawPassword, utilisateur.getPassword())
        ).orElse(false);
    }

    // ✅ Méthode manquante à ajouter ici
    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}
