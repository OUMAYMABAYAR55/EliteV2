package com.example.demo.springbootdb.controller;

import com.example.demo.springbootdb.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/reserver/{eventId}/{enfantId}")
    public ResponseEntity<String> reserverPlace(
            @PathVariable Long eventId,
            @PathVariable Long enfantId) {
        return ResponseEntity.ok(eventService.reserverPlace(enfantId, eventId));
    }

    @PostMapping("/admin/actualite/{eventId}")
    public ResponseEntity<String> publierActualite(
            @PathVariable Long eventId,
            @RequestParam String titre,
            @RequestParam String contenu) {
        return ResponseEntity.ok(eventService.publierActualiteParAdmin(eventId, titre, contenu));
    }
}
