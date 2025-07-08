package com.example.demo.springbootdb.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idE;
    private String type;
    private String titre;
    private Date date;
    private int nbPlaces;
}
