package com.example.demo.springbootdb.controller;

import com.example.demo.springbootdb.Models.Enfant;
import com.example.demo.springbootdb.Models.Utilisateur;
import com.example.demo.springbootdb.repository.EnfantRepository;
import com.example.demo.springbootdb.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/parent/enfants")
public class EnfantController {

    @Autowired
    private EnfantRepository enfantRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping
    public ResponseEntity<?> ajouterEnfant(@RequestBody Enfant enfant, Principal principal) {
        try {
            String email = principal.getName();
            Utilisateur parent = utilisateurRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Parent non trouvé"));
            enfant.setParent(parent);
            Enfant saved = enfantRepository.save(enfant);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'ajout de l'enfant : " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> mesEnfants(Principal principal) {
        try {
            String email = principal.getName();
            Utilisateur parent = utilisateurRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Parent non trouvé"));
            List<Enfant> enfants = enfantRepository.findByParent(parent);
            return ResponseEntity.ok(enfants);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de la récupération des enfants : " + e.getMessage());
        }
    }
}
