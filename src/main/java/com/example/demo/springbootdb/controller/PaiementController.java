package com.example.demo.springbootdb.controller;

import com.example.demo.springbootdb.Models.Paiement;
import com.example.demo.springbootdb.service.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController {

    @Autowired
    private PaiementService paiementService;

    @GetMapping
    public List<Paiement> getAll() {
        return paiementService.getAllPaiements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paiement> getById(@PathVariable Long id) {
        return paiementService.getPaiementById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('SECRETAIRE')")
    public ResponseEntity<Paiement> create(@RequestBody Paiement paiement) {
        Paiement created = paiementService.createPaiement(paiement);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SECRETAIRE')")
    public ResponseEntity<Paiement> update(@PathVariable Long id, @RequestBody Paiement paiement) {
        return ResponseEntity.ok(paiementService.updatePaiement(id, paiement));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SECRETAIRE')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paiementService.deletePaiement(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/statut")
    @PreAuthorize("hasRole('SECRETAIRE')")
    public ResponseEntity<Paiement> changerStatut(@PathVariable Long id,
                                                  @RequestParam String statut) {
        return ResponseEntity.ok(paiementService.changerStatutPaiement(id, statut));
    }


    @GetMapping("/utilisateur/{id}")
    @PreAuthorize("hasRole('PARENT')")
    public List<Paiement> getByUtilisateur(@PathVariable Long id) {
        return paiementService.getPaiementsByUtilisateurId(id);
    }
}
