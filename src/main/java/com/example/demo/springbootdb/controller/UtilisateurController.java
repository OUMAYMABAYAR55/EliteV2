package com.example.demo.springbootdb.controller;

import com.example.demo.springbootdb.Models.Utilisateur;
import com.example.demo.springbootdb.service.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping
    public List<Utilisateur> getAll() {
        return utilisateurService.findAll();
    }

    @PostMapping
    public Utilisateur create(@Valid @RequestBody Utilisateur utilisateur) {
        return utilisateurService.save(utilisateur);
    }

    @GetMapping("/{id}")
    public Utilisateur getById(@PathVariable Long id) {
        return utilisateurService.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @PutMapping("/{id}")
    public Utilisateur update(@PathVariable Long id, @Valid @RequestBody Utilisateur utilisateur) {
        Utilisateur existant = utilisateurService.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        utilisateur.setId(existant.getId());
        return utilisateurService.save(utilisateur);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        utilisateurService.deleteById(id);
        return "Utilisateur supprimé";
    }
}