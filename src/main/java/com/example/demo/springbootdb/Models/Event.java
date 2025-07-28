package com.example.demo.springbootdb.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    @Column(nullable = false)
    private String type; // "FORMATION" ou "PROJET"

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Utilisateur parent;

    // Autres champs optionnels (date, description...)
}
