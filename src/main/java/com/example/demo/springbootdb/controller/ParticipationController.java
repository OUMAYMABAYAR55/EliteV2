package com.example.demo.springbootdb.controller;

import com.example.demo.springbootdb.Models.Participation;
import com.example.demo.springbootdb.service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/participations")
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;

    @PostMapping
    public Participation participer(@RequestBody Participation participation) {
        return participationService.enregistrerParticipation(participation);
    }
}
