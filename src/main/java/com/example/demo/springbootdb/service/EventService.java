package com.example.demo.springbootdb.service;

import com.example.demo.springbootdb.Models.*;
import com.example.demo.springbootdb.controller.NotificationController;
import com.example.demo.springbootdb.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepo;
    private final ParticipationRepository participationRepo;
    private final ActualiteRepository actualiteRepo;
    private final NotificationRepository notificationRepo;
    private final EnfantRepository enfantRepo;
    private NotificationController notificationController;

    public EventService(EventRepository eventRepo,
                        ParticipationRepository participationRepo,
                        ActualiteRepository actualiteRepo,
                        NotificationRepository notificationRepo,
                        EnfantRepository enfantRepo,
                        NotificationController notificationController) {
        this.eventRepo = eventRepo;
        this.participationRepo = participationRepo;
        this.actualiteRepo = actualiteRepo;
        this.notificationRepo = notificationRepo;
        this.enfantRepo = enfantRepo;
        this.notificationController = notificationController;
    }

    // ✅ Ajouter ou mettre à jour un événement
    public Event ajouterEvenement(Event event) {
        return eventRepo.save(event);
    }

    // ✅ Modifier un événement
    public Event modifierEvenement(Long id, Event updatedEvent) {
        Event existingEvent = eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
        existingEvent.setType(updatedEvent.getType());
        existingEvent.setTitre(updatedEvent.getTitre());
        existingEvent.setDate(updatedEvent.getDate());
        existingEvent.setNbPlaces(updatedEvent.getNbPlaces());
        existingEvent.setImage(updatedEvent.getImage());
        return eventRepo.save(existingEvent);
    }

    // ✅ Supprimer un événement
    public void supprimerEvenement(Long id) {
        if (!eventRepo.existsById(id)) {
            throw new RuntimeException("Événement non trouvé");
        }
        eventRepo.deleteById(id);
    }

    // ✅ Récupérer tous les événements
    public List<Event> findAll() {
        return eventRepo.findAll();
    }

    // ✅ Récupérer un événement par ID
    public Event getEventById(Long id) {
        return eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
    }

    // ✅ Réserver une place
    public String reserverPlace(Long enfantId, Long eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));

        if (event.getNbPlaces() <= 0) return "Aucune place disponible.";

        Enfant enfant = enfantRepo.findById(enfantId)
                .orElseThrow(() -> new RuntimeException("Enfant non trouvé"));

        boolean existe = participationRepo.existsByEnfantAndEvent(enfant, event);
        if (existe) return "L'enfant est déjà inscrit.";

        Participation p = new Participation();
        p.setDateinscription(new Date());
        p.setStatuts("CONFIRMÉ");
        p.setEvent(event);
        p.setEnfant(enfant);
        participationRepo.save(p);

        event.setNbPlaces(event.getNbPlaces() - 1);
        eventRepo.save(event);

        return "Réservation réussie.";
    }

    // ✅ Publier actualité avec notifications
    public String publierActualiteParAdmin(Long eventId, String titre, String contenu) {
        // Récupérer l'événement
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));

        // Créer et sauvegarder l'actualité liée à l'événement
        Actualite actualite = new Actualite();
        actualite.setTitre(titre);
        actualite.setContenu(contenu);
        actualite.setDatePublication(LocalDateTime.now());
        actualite.setEvent(event);
        actualiteRepo.save(actualite);

        // Récupérer toutes les participations à cet événement
        List<Participation> participations = participationRepo.findByEvent(event);

        // Pour chaque participation, récupérer le parent et envoyer une notification
        for (Participation p : participations) {
            Utilisateur parent = p.getEnfant().getParent();
            if (parent != null) {
                // Créer la notification en base
                Notification notif = new Notification();
                notif.setContenu("Nouvelle actualité : " + titre);
                notif.setDateEnvoi(new Date());
                notif.setUtilisateur(parent);
                notificationRepo.save(notif);

                // Envoyer la notification en temps réel via WebSocket
                notificationController.envoyerNotification(notif);
            }
        }

        return "Actualité publiée avec notifications.";
    }

}
