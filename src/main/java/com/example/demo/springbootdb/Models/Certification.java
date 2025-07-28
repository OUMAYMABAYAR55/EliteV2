package com.example.demo.springbootdb.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "certification")
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Le donneur du certificat (ex: admin, gerant, secretaire)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donne_par_id", nullable = false)
    private Utilisateur donnePar;

    // Le parent qui reçoit le certificat
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = false)
    private Utilisateur parent;

    @Column(name = "titre", nullable = false)
    private String titre;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    public Certification() {
        this.dateCreation = LocalDateTime.now();
    }
}
