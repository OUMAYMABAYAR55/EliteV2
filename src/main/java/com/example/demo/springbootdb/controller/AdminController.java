package com.example.demo.springbootdb.controller;

import com.example.demo.springbootdb.Models.Event;
import com.example.demo.springbootdb.Models.Role;
import com.example.demo.springbootdb.service.EnfantService;
import com.example.demo.springbootdb.service.EventService;
import com.example.demo.springbootdb.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private EventService evenementService;

    @Autowired
    private EnfantService enfantService; // ✅ Ajouté pour compter les membres réels

    // ✅ Activer un parent
    @PutMapping("/parent/activer/{id}")
    public ResponseEntity<?> activerParent(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.activerParent(id));
    }

    // ✅ Désactiver un parent
    @PutMapping("/parent/desactiver/{id}")
    public ResponseEntity<?> desactiverParent(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.desactiverParent(id));
    }

    // ✅ Ajouter un événement
    @PostMapping("/evenement")
    public ResponseEntity<?> ajouterEvenement(@RequestBody Event evenement) {
        return ResponseEntity.ok(evenementService.ajouterEvenement(evenement));
    }

    // ✅ Upload image pour un événement
    @PostMapping("/evenement/{id}/image")
    public ResponseEntity<?> uploadEventImage(@PathVariable Long id,
                                              @RequestParam("file") MultipartFile file) {
        try {
            Event event = evenementService.getEventById(id); // ✅ Récupération
            if (event == null) {
                return ResponseEntity.status(404).body("Événement non trouvé");
            }

            // ✅ Créer le dossier si inexistant
            String uploadDir = "uploads/images/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            // ✅ Sauvegarder le fichier
            String filename = "event_" + id + "_" + file.getOriginalFilename();
            File dest = new File(uploadDir + filename);
            file.transferTo(dest);

            // ✅ Mettre à jour l'image de l'événement
            event.setImage(filename);
            evenementService.ajouterEvenement(event);  // ⚡ update sans recréer

            return ResponseEntity.ok("Image uploadée avec succès");

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erreur lors de l'upload : " + e.getMessage());
        }
    }

    // ✅ Modifier un événement
    @PutMapping("/evenement/{id}")
    public ResponseEntity<?> modifierEvenement(@PathVariable Long id, @RequestBody Event evenement) {
        return ResponseEntity.ok(evenementService.modifierEvenement(id, evenement));
    }

    // ✅ Supprimer un événement
    @DeleteMapping("/evenement/{id}")
    public ResponseEntity<?> supprimerEvenement(@PathVariable Long id) {
        evenementService.supprimerEvenement(id);
        return ResponseEntity.ok("Événement supprimé");
    }

    // ✅ Lister les événements
    @GetMapping("/evenement")
    public ResponseEntity<?> listEvenement() {
        return ResponseEntity.ok(evenementService.findAll());
    }

    // ✅ Statistiques des membres
    @GetMapping("/stats")
    public ResponseEntity<?> statistiques() {
        long totalMembres = enfantService.countAllEnfants(); // ✅ On compte les enfants ici
        long totalParentsActifs = utilisateurService.countUtilisateursParRole(Role.PARENT);
        long totalActifs = utilisateurService.countUtilisateursActifs();

        Map<String, Long> stats = new HashMap<>();
        stats.put("parents", totalParentsActifs);
        stats.put("actifs", totalActifs);

        return ResponseEntity.ok(stats);
    }
}


//acceptee parent , desactivee parent
//ajouter event , actualitee (supp,add,modif)
//afficher statistique de membre