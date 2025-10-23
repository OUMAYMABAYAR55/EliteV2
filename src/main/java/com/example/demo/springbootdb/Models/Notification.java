package com.example.demo.springbootdb.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contenu;

    private Date dateEnvoi;
    //attribut bool vu

    @ManyToOne
    private Utilisateur utilisateur; // le parent
}
