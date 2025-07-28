package com.example.demo.springbootdb.repository;

import com.example.demo.springbootdb.Models.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    // Ajoute cette méthode pour que Spring Data génère la requête automatiquement
    List<Certification> findByParentId(Long parentId);

}