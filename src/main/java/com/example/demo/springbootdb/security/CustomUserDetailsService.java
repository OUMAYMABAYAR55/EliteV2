package com.example.demo.springbootdb.security;

import com.example.demo.springbootdb.Models.Utilisateur;
import com.example.demo.springbootdb.repository.UtilisateurRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository repository;

    public CustomUserDetailsService(UtilisateurRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Cherche l'utilisateur par email dans la BDD
        Utilisateur user = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec email: " + email));

        // Log dans la console pour vérifier le rôle récupéré
        System.out.println("Utilisateur trouvé : " + user.getEmail() + ", rôle: " + user.getRole().name());

        // Retourne un UserDetails Spring Security avec rôle formaté ROLE_XXX
        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().name())  // IMPORTANT : le rôle doit commencer par "ROLE_"
                .build();
    }
}
