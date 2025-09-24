package com.example.demo.springbootdb.controller;

import com.example.demo.springbootdb.Models.Event;
import com.example.demo.springbootdb.service.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/admin/evenement")
@CrossOrigin(origins = "http://localhost:4200")
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private EventService eventService;

    private static final String UPLOAD_DIR = "uploads/images/";

    // ✅ ENDPOINT PUBLIC POUR TOUS LES ÉVÉNEMENTS
    @GetMapping("/public")
    public List<Event> getPublicEvents() {
        logger.info("📥 GET tous les événements (public)");
        return eventService.findAll();
    }

    // ✅ ENDPOINT PUBLIC POUR LES IMAGES
    @GetMapping("/public/image/{filename:.+}")
    public ResponseEntity<Resource> getPublicImage(@PathVariable String filename) {
        logger.info("🖼️ Image publique demandée: {}", filename);
        return getImage(filename);
    }

    // ✅ ENDPOINT DE TEST PUBLIC
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        logger.info("🧪 Test endpoint appelé");
        return ResponseEntity.ok("✅ EventController est accessible !");
    }

    @GetMapping
    public List<Event> getAllEvents() {
        logger.info("📥 GET tous les événements");
        return eventService.findAll();
    }
    // ✅ ENDPOINT PUBLIC POUR FILTRER PAR TYPE (AJOUTEZ-CETTE MÉTHODE)
    @GetMapping("/public/type/{type}")
    public List<Event> getEventsByType(@PathVariable String type) {
        logger.info("📥 GET événements par type: {}", type);
        return eventService.findByType(type);
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        logger.info("➕ POST nouvel événement: {}", event.getTitre());
        logger.info("📋 Détails - Type: {}, Date: {}, Places: {}",
                event.getType(), event.getDate(), event.getNbPlaces());

        Event savedEvent = eventService.save(event);
        logger.info("✅ Événement créé ID: {}", savedEvent.getIdE());
        return savedEvent;
    }

    @PutMapping("/{id}")
    public Event updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        logger.info("✏️ PUT modification événement ID: {}", id);

        Event event = eventService.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        event.setTitre(eventDetails.getTitre());
        event.setType(eventDetails.getType());
        event.setDate(eventDetails.getDate());
        event.setNbPlaces(eventDetails.getNbPlaces());
        event.setImage(eventDetails.getImage());

        return eventService.save(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        logger.info("🗑️ DELETE événement ID: {}", id);

        if (!eventService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        eventService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<String> uploadImage(@PathVariable Long id,
                                              @RequestParam("file") MultipartFile file) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath);

            Event event = eventService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
            event.setImage(fileName);
            eventService.save(event);

            logger.info("🖼️ Image uploadée: {}", fileName);
            return ResponseEntity.ok("Image uploaded: " + fileName);
        } catch (IOException e) {
            logger.error("❌ Erreur upload image: {}", e.getMessage());
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // 🖼️ MÉTHODE POUR SERVIR LES IMAGES
    @GetMapping("/image/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get(UPLOAD_DIR).resolve(filename).toAbsolutePath().normalize();
            Resource resource = new UrlResource(imagePath.toUri());

            logger.info("🖼️ Tentative chargement image: {}", imagePath);

            if (resource.exists() && resource.isReadable()) {
                logger.info("✅ Image trouvée: {}", filename);

                // Déterminer le type MIME
                String mimeType = Files.probeContentType(imagePath);
                if (mimeType == null) {
                    mimeType = "application/octet-stream";
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(mimeType))
                        .body(resource);
            } else {
                logger.warn("❌ Image non trouvée: {}", filename);
            }
        } catch (Exception e) {
            logger.error("❌ Erreur chargement image {}: {}", filename, e.getMessage());
        }
        return ResponseEntity.notFound().build();
    }

    // 🔍 ENDPOINT DE DEBUG
    @GetMapping("/debug/path")
    public ResponseEntity<String> debugPath() {
        try {
            String currentDir = System.getProperty("user.dir");
            Path uploadPath = Paths.get(UPLOAD_DIR);
            Path absolutePath = Paths.get(currentDir).resolve(uploadPath);

            StringBuilder response = new StringBuilder();
            response.append("📁 Current directory: ").append(currentDir).append("\n");
            response.append("📁 Upload path: ").append(absolutePath).append("\n");
            response.append("📁 Exists: ").append(Files.exists(absolutePath)).append("\n");

            if (Files.exists(absolutePath)) {
                response.append("📁 Files in directory:\n");
                try (var files = Files.list(absolutePath)) {
                    files.forEach(file -> response.append(" - ").append(file.getFileName()).append("\n"));
                }
            }

            return ResponseEntity.ok(response.toString());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}