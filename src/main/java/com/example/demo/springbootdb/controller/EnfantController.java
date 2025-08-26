package com.example.demo.springbootdb.controller;

import com.example.demo.springbootdb.Models.Enfant;
import com.example.demo.springbootdb.repository.EnfantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enfants") // différent de /api/parent/enfants
public class EnfantController {

    @Autowired
    private EnfantRepository enfantRepository;

    // Liste globale (admin)
    @GetMapping("/all")
    public ResponseEntity<List<Enfant>> getAllEnfants() {
        return ResponseEntity.ok(enfantRepository.findAll());
    }
}
