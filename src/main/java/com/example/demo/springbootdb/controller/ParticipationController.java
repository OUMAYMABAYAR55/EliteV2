package com.example.demo.springbootdb.controller;
import com.example.demo.springbootdb.Models.Participation;
import com.example.demo.springbootdb.service.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/participations")
public class ParticipationController {
    @Autowired
    ParticipationService participationService;
    @GetMapping
    public List<Participation> getAllParticipations() {
        return participationService.findAll();
    }
    @PostMapping
    public Participation saveParticipation(@RequestBody Participation participation) {
        return participationService.save(participation);
    }
}
