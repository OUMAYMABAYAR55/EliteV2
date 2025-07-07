package com.example.demo.springbootdb.repository;

import com.example.demo.springbootdb.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    boolean existsByEmail(String email);
}
