package com.example.demo.springbootdb.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ide")
    private Long idE;

    private String titre;

    @Column(nullable = false)
    private String type;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "nb_places")
    private Integer nbPlaces;

    private String image;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Utilisateur parent;

    // Constructeurs
    public Event() {}

    public Event(String titre, String type, LocalDate date, Integer nbPlaces, String image) {
        this.titre = titre;
        this.type = type;
        this.date = date;
        this.nbPlaces = nbPlaces;
        this.image = image;
    }

    // ✅ MÉTHODES POUR LES IMAGES
    public String getImageUrl() {
        if (this.image != null && !this.image.isEmpty()) {
            return "http://localhost:8081/uploads/" + this.image;
        }
        return null;
    }

    public String getApiImageUrl() {
        if (this.image != null && !this.image.isEmpty()) {
            return "http://localhost:8081/api/admin/evenement/image/" + this.image;
        }
        return null;
    }
}