package com.example.demo.springbootdb.repository;

import com.example.demo.springbootdb.Models.Enfant;
import com.example.demo.springbootdb.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnfantRepository extends JpaRepository<Enfant, Long> {
    List<Enfant> findByParent(Utilisateur parent);

    // Optionnel : méthode pour récupérer par email du parent
    default List<Enfant> findByParentEmail(String email) {
        // Si tu as un UtilisateurRepository injecté, tu peux résoudre ici
        throw new UnsupportedOperationException("À implémenter si nécessaire");
    }
}
