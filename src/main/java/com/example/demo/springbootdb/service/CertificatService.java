package com.example.demo.springbootdb.service;

import com.example.demo.springbootdb.Models.Certificat;
import com.example.demo.springbootdb.Models.Role;
import com.example.demo.springbootdb.Models.Utilisateur;
import com.example.demo.springbootdb.repository.CertificatRepository;
import com.example.demo.springbootdb.repository.UtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CertificatService {

    private final CertificatRepository certificatRepository;
    private final UtilisateurRepository utilisateurRepository;

    // Rôles autorisés à créer un certificat
    private static final Set<Role> ROLES_AUTORISES = Set.of(Role.ADMIN, Role.GERANT, Role.SECRETAIRE);

    public CertificatService(CertificatRepository certificatRepository, UtilisateurRepository utilisateurRepository) {
        this.certificatRepository = certificatRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public Certificat creerCertificat(Long idDonneur, Long idParent) {
        Utilisateur donneur = utilisateurRepository.findById(idDonneur)
                .orElseThrow(() -> new RuntimeException("Donneur non trouvé"));

        Utilisateur parent = utilisateurRepository.findById(idParent)
                .orElseThrow(() -> new RuntimeException("Parent non trouvé"));

        // Vérification rôle du donneur
        if (!ROLES_AUTORISES.contains(donneur.getRole())) {
            throw new RuntimeException("Seul ADMIN, GERANT ou SECRETAIRE peut créer un certificat");
        }

        // Vérification des conditions du parent
        if (parent.getFormationsValidees() < 3) {
            throw new RuntimeException("Le parent doit avoir validé au moins 3 formations");
        }

        if (parent.getProjetsRealises() < 1) {
            throw new RuntimeException("Le parent doit avoir réalisé au moins 1 projet");
        }

        // Création du certificat
        Certificat certificat = new Certificat();
        certificat.setDonnePar(donneur); // CHAMP CORRECT UTILISÉ ICI
        certificat.setParent(parent);
        certificat.setTitre("Certificat validé"); // Exemple de titre

        return certificatRepository.save(certificat);
    }

    public List<Certificat> findAll() {
        return certificatRepository.findAll();
    }

    public List<Certificat> findByParentId(Long parentId) {
        return certificatRepository.findByParentId(parentId);
    }
}
