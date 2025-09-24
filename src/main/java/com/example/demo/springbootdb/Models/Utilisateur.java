package com.example.demo.springbootdb.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100)
    @Column(name = "nom", nullable = false)
    private String nom;

    // AJOUTE CE CHAMP MANQUANT
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 100)
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotBlank(message = "L'email est obligatoire")
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Size(min = 6)
    private String password;

    @Size(max = 15)
    @Column(name = "telephone")
    private String telephone;

    @Size(max = 255)
    @Column(name = "adresse")
    private String adresse;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role = Role.MEMBRE; // Valeur par défaut

    @Column(name = "formations_validees")
    private int formationsValidees = 0;

    @Column(name = "projets_realises")
    private int projetsRealises = 0;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification")
    private LocalDateTime dateModification;

    @Column(name = "actif")
    private Boolean actif = true;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Certificat> certificats;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Event> events;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Participation> participations;

    // Constructeurs
    public Utilisateur() {
        this.dateCreation = LocalDateTime.now();
        this.role = Role.MEMBRE; // Rôle par défaut
    }

    public Utilisateur(String nom, String prenom, String email, String password) {
        this();
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
    }

    @PreUpdate
    public void preUpdate() {
        this.dateModification = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}