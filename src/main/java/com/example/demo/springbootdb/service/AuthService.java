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
        try {
            // Vérifier si l'email existe déjà
            if (utilisateurRepository.existsByEmail(utilisateur.getEmail())) {
                return "Email déjà utilisé.";
            }

            // Valider les champs obligatoires
            if (utilisateur.getNom() == null || utilisateur.getNom().trim().isEmpty()) {
                return "Le nom est obligatoire.";
            }

            if (utilisateur.getPrenom() == null || utilisateur.getPrenom().trim().isEmpty()) {
                return "Le prénom est obligatoire.";
            }

            if (utilisateur.getEmail() == null || utilisateur.getEmail().trim().isEmpty()) {
                return "L'email est obligatoire.";
            }

            if (utilisateur.getPassword() == null || utilisateur.getPassword().length() < 6) {
                return "Le mot de passe doit contenir au moins 6 caractères.";
            }

            // Encoder le mot de passe
            utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));

            // Définir un rôle par défaut si non fourni
            if (utilisateur.getRole() == null) {
                utilisateur.setRole(Role.MEMBRE);
            }

            // Sauvegarder l'utilisateur
            utilisateurRepository.save(utilisateur);
            return "Utilisateur enregistré avec succès.";

        } catch (Exception e) {
            e.printStackTrace(); // Pour le débogage
            return "Erreur lors de l'enregistrement: " + e.getMessage();
        }
    }

    public boolean login(String email, String rawPassword) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(email);
        return utilisateurOpt.map(utilisateur ->
                passwordEncoder.matches(rawPassword, utilisateur.getPassword())
        ).orElse(false);
    }

    public Utilisateur findByEmail(String email) {
        return utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }
}