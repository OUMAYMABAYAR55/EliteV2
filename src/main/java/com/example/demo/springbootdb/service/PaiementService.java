package com.example.demo.springbootdb.service;

import com.example.demo.springbootdb.Models.Paiement;
import com.example.demo.springbootdb.repository.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    public Optional<Paiement> getPaiementById(Long id) {
        return paiementRepository.findById(id);
    }

    public List<Paiement> getPaiementsByUtilisateurId(Long utilisateurId) {
        return paiementRepository.findByUtilisateurId(utilisateurId);
    }

    public Paiement createPaiement(Paiement paiement) {
        paiement.setDatePaiement(LocalDateTime.now());
        return paiementRepository.save(paiement);
    }

    public Paiement updatePaiement(Long id, Paiement updatedPaiement) {
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement non trouvé"));

        paiement.setMontant(updatedPaiement.getMontant());
        paiement.setStatut(updatedPaiement.getStatut());
        paiement.setDatePaiement(LocalDateTime.now());

        return paiementRepository.save(paiement);
    }

    public void deletePaiement(Long id) {
        paiementRepository.deleteById(id);
    }

    public Paiement changerStatutPaiement(Long id, String statut) {
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement introuvable"));

        paiement.setStatut(statut);
        paiement.setDatePaiement(LocalDateTime.now());

        return paiementRepository.save(paiement);
    }
}
