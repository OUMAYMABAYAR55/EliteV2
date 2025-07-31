package com.example.demo.springbootdb.repository;

import com.example.demo.springbootdb.Models.Role;
import com.example.demo.springbootdb.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    boolean existsByEmail(String email);

    Utilisateur save(Utilisateur utilisateur);

    Optional<Utilisateur> findByEmail(String email);
    long countByRole(Role role);
    long countByActif(boolean actif);

}