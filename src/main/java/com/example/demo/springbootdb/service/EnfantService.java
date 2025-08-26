package com.example.demo.springbootdb.service;

import com.example.demo.springbootdb.Models.Enfant;
import com.example.demo.springbootdb.Models.Utilisateur;
import com.example.demo.springbootdb.repository.EnfantRepository;
import com.example.demo.springbootdb.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnfantService {

    @Autowired
    private EnfantRepository enfantRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public long countAllEnfants() {
        return enfantRepository.count();
    }

    public Enfant ajouterEnfant(Enfant enfant, String parentEmail) {
        Utilisateur parent = utilisateurRepository.findByEmail(parentEmail)
                .orElseThrow(() -> new RuntimeException("Parent non trouvé"));
        enfant.setParent(parent);
        return enfantRepository.save(enfant);
    }

    public List<Enfant> getEnfants(String parentEmail) {
        Utilisateur parent = utilisateurRepository.findByEmail(parentEmail)
                .orElseThrow(() -> new RuntimeException("Parent non trouvé"));
        return enfantRepository.findByParent(parent);
    }
}
