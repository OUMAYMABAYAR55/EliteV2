package com.example.demo.springbootdb.repository;

import com.example.demo.springbootdb.Models.Certificat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificatRepository extends JpaRepository<Certificat, Long> {

    // Ajoute cette méthode pour que Spring Data génère la requête automatiquement
    List<Certificat> findByParentId(Long parentId);

}
