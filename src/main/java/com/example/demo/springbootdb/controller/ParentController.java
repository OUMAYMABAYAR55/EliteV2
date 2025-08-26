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

    private final EnfantRepository enfantRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final EnfantService enfantService;

    public ParentController(EnfantService enfantService,
                            UtilisateurRepository utilisateurRepository,
                            EnfantRepository enfantRepository) {
        this.enfantService = enfantService;
        this.utilisateurRepository = utilisateurRepository;
        this.enfantRepository = enfantRepository;
    }

    // Profil parent
    @GetMapping("/profil")
    public ResponseEntity<Utilisateur> getProfil(Principal principal) {
        Utilisateur user = utilisateurRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profil")
    public ResponseEntity<Utilisateur> updateProfil(@RequestBody Utilisateur majProfil, Principal principal) {
        Utilisateur user = utilisateurRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setNom(majProfil.getNom());
        user.setPrenom(majProfil.getPrenom());
        user.setTelephone(majProfil.getTelephone());
        user.setAdresse(majProfil.getAdresse());
        utilisateurRepository.save(user);
        return ResponseEntity.ok(user);
    }

    // Ajouter un enfant
    @PostMapping("/enfants")
    public ResponseEntity<Enfant> addEnfant(@RequestBody Enfant enfant, Principal principal) {
        Utilisateur parent = utilisateurRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Parent non trouvé"));
        enfant.setParent(parent); // lier l’enfant au parent connecté
        Enfant saved = enfantRepository.save(enfant);
        return ResponseEntity.ok(saved);
    }

    // Liste des enfants du parent
    @GetMapping("/enfants")
    public ResponseEntity<List<Enfant>> mesEnfants(Principal principal) {
        return ResponseEntity.ok(enfantService.getEnfants(principal.getName()));
    }
}
