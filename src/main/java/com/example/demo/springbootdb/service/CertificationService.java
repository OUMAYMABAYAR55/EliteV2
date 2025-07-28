package com.example.demo.springbootdb.service;

import com.example.demo.springbootdb.Models.Certification;
import com.example.demo.springbootdb.Models.Role;
import com.example.demo.springbootdb.Models.Utilisateur;
import com.example.demo.springbootdb.repository.CertificationRepository;
import com.example.demo.springbootdb.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final UtilisateurRepository utilisateurRepository;

    private static final Set<Role> ROLES_AUTORISES = Set.of(Role.ADMIN, Role.GERANT, Role.SECRETAIRE);

    public CertificationService(CertificationRepository certificationRepository, UtilisateurRepository utilisateurRepository) {
        this.certificationRepository = certificationRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public Certification creerCertification(Long idDonneur, Long idParent) {
        Utilisateur donneur = utilisateurRepository.findById(idDonneur)
                .orElseThrow(() -> new RuntimeException("Donneur non trouvé"));

        Utilisateur parent = utilisateurRepository.findById(idParent)
                .orElseThrow(() -> new RuntimeException("Parent non trouvé"));

        if (!ROLES_AUTORISES.contains(donneur.getRole())) {
            throw new RuntimeException("Seul ADMIN, GERANT ou SECRETAIRE peut créer un certificat");
        }

        // Suppression des vérifications sur formationsValidees et projetsRealises
        // Si tu souhaites, tu peux remplacer par une autre logique métier ici

        Certification certificat = new Certification();
        certificat.setDonnePar(donneur);
        certificat.setParent(parent);
        certificat.setTitre("Certificat validé");

        return certificationRepository.save(certificat);
    }

    public List<Certification> findAll() {
        return certificationRepository.findAll();
    }

    public List<Certification> findByParentId(Long parentId) {
        return certificationRepository.findByParentId(parentId);
    }
}
