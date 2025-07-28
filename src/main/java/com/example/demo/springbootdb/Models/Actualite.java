package com.example.demo.springbootdb.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Actualite {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idA;

        private String titre;

        private String contenu;

        private String auteur;  // Added missing field

        private LocalDateTime datePublication;  // Changed from datePub to datePublication and Date to LocalDateTime

        @OneToOne
        private Event event;
}