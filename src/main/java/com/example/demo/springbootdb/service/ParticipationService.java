package com.example.demo.springbootdb.service;

import com.example.demo.springbootdb.Models.Participation;
import com.example.demo.springbootdb.Models.Utilisateur;
import com.example.demo.springbootdb.repository.ParticipationRepository;
import com.example.demo.springbootdb.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

@Service
public class ParticipationService {

    private final ParticipationRepository participationRepository;
    private final UtilisateurRepository utilisateurRepository;

    public ParticipationService(ParticipationRepository participationRepository, UtilisateurRepository utilisateurRepository) {
        this.participationRepository = participationRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public Participation enregistrerParticipation(Participation participation) {
        Participation saved = participationRepository.save(participation);

        Utilisateur parent = participation.getParent();
        String type = participation.getEvent().getType();

        if ("FORMATION".equalsIgnoreCase(type)) {
            parent.setFormationsValidees(parent.getFormationsValidees() + 1);
        } else if ("PROJET".equalsIgnoreCase(type)) {
            parent.setProjetsRealises(parent.getProjetsRealises() + 1);
        }

        utilisateurRepository.save(parent);

        return saved;
    }
}
