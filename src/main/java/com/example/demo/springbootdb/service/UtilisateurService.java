package com.example.demo.springbootdb.service;
import com.example.demo.springbootdb.Models.Role;

import com.example.demo.springbootdb.Models.Utilisateur;
import com.example.demo.springbootdb.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional; //test token mawjouda wle , actif ,role tous les test possibe

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    private PasswordEncoder passwordEncoder;
    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> findById(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Utilisateur save(Utilisateur utilisateur) {
        return utilisateurRepository.save(utilisateur);
    }

    public void deleteById(Long id) {
        utilisateurRepository.deleteById(id);
    }
    public Utilisateur activerParent(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        if (utilisateur.getRole() == Role.PARENT) {
            utilisateur.setActif(true);
            return utilisateurRepository.save(utilisateur);
        }
        throw new RuntimeException("L'utilisateur n'est pas un parent");
    }

    public Utilisateur desactiverParent(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        if (utilisateur.getRole() == Role.PARENT) {
            utilisateur.setActif(false);
            return utilisateurRepository.save(utilisateur);
        }
        throw new RuntimeException("L'utilisateur n'est pas un parent");
    }
    public void updatePassword(String email, String newPassword) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        utilisateur.setPassword(passwordEncoder.encode(newPassword));
        utilisateurRepository.save(utilisateur);
    }
    public long countUtilisateursParRole(Role role) {
        return utilisateurRepository.countByRole(role);
    }

    public long countUtilisateursActifs() {
        return utilisateurRepository.countByActif(true);
    }
}
