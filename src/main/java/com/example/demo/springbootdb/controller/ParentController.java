package com.example.demo.springbootdb.controller;

import com.example.demo.springbootdb.Models.Enfant;
import com.example.demo.springbootdb.Models.Utilisateur;
import com.example.demo.springbootdb.repository.EnfantRepository;
import com.example.demo.springbootdb.repository.UtilisateurRepository;
import com.example.demo.springbootdb.service.EnfantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/parent")
public class ParentController {
        private final EnfantService enfantService;
        private final UtilisateurRepository utilisateurRepository;

        public ParentController(EnfantService enfantService,
                                UtilisateurRepository utilisateurRepository) {
            this.enfantService = enfantService;
            this.utilisateurRepository = utilisateurRepository;
        }

        // ✅ Récupérer le profil du parent connecté
        @GetMapping("/profil")
        public ResponseEntity<Utilisateur> getProfil(Principal principal) {
            Utilisateur user = utilisateurRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            return ResponseEntity.ok(user);
        }

        // ✅ Récupérer la liste des enfants du parent
        @GetMapping("/enfants")
        public ResponseEntity<List<Enfant>> mesEnfants(Principal principal) {
            return ResponseEntity.ok(enfantService.getEnfants(principal.getName()));
        }
    }