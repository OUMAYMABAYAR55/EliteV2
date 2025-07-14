package com.example.demo.springbootdb.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Actualite {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idA;

        private String titre;

        private String contenu; // 🔁 Corrigé

        private Date datePub;

        @OneToOne
        private Event event;
}
