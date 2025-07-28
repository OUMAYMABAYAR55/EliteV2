package com.example.demo.springbootdb.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "participation")
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Utilisateur parent;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    // Champs supplémentaires si besoin (date, statut…)
}
