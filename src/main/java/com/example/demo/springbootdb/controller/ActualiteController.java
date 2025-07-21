package com.example.demo.springbootdb.controller;

import com.example.demo.springbootdb.Models.Actualite;
import com.example.demo.springbootdb.service.ActualiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actualites")
public class ActualiteController {

    @Autowired
    private ActualiteService actualiteService;

    @GetMapping
    public List<Actualite> getAll() {
        return actualiteService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actualite> getById(@PathVariable Long id) {
        return actualiteService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERANT', 'SECRETAIRE')")
    public ResponseEntity<Actualite> create(@RequestBody Actualite actualite) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(actualiteService.create(actualite));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERANT', 'SECRETAIRE')")
    public ResponseEntity<Actualite> update(@PathVariable Long id, @RequestBody Actualite actualite) {
        return ResponseEntity.ok(actualiteService.update(id, actualite));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        actualiteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
