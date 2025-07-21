package com.example.demo.springbootdb.repository;

import com.example.demo.springbootdb.Models.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByStatut(String statut);
    List<Paiement> findByUtilisateurId(Long utilisateurId);
}
