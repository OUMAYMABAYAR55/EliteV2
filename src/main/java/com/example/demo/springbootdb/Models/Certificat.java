package com.example.demo.springbootdb.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "certificat")
public class Certificat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Utilisateur parent; // celui qui reçoit le certificat

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donne_par_id")
    private Utilisateur donnePar; // celui qui délivre (ADMIN, GERANT, SECRETAIRE)
}
