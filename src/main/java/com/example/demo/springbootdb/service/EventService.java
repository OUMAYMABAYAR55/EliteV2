package com.example.demo.springbootdb.service;

import com.example.demo.springbootdb.Models.*;
import com.example.demo.springbootdb.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Date;

@Service
public class EventService {

    private final EventRepository eventRepo;
    private final ParticipationRepository participationRepo;
    private final ActualiteRepository actualiteRepo;
    private final NotificationRepository notificationRepo;
    private final EnfantRepository enfantRepo;

    public EventService(EventRepository eventRepo,
                        ParticipationRepository participationRepo,
                        ActualiteRepository actualiteRepo,
                        NotificationRepository notificationRepo,
                        EnfantRepository enfantRepo) {
        this.eventRepo = eventRepo;
        this.participationRepo = participationRepo;
        this.actualiteRepo = actualiteRepo;
        this.notificationRepo = notificationRepo;
        this.enfantRepo = enfantRepo;
    }

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

    public String publierActualiteParAdmin(Long eventId, String titre, String contenu) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));

        Actualite actualite = new Actualite();
        actualite.setTitre(titre);
        actualite.setContenu(contenu);
        actualite.setDatePublication(LocalDateTime.now()); // Changed from setDatePub(new Date())
        actualite.setEvent(event);
        actualiteRepo.save(actualite);

        List<Participation> participations = participationRepo.findByEvent(event);
        Set<Utilisateur> parentsNotifies = new HashSet<>();

        for (Participation p : participations) {
            Utilisateur parent = p.getEnfant().getParent();
            if (parent != null && !parentsNotifies.contains(parent)) {
                Notification notif = new Notification();
                notif.setContenu("Nouvelle actualité : " + titre);
                notif.setDateEnvoi(new Date());
                notif.setUtilisateur(parent);
                notificationRepo.save(notif);
                parentsNotifies.add(parent);
            }
        }

        return "Actualité publiée avec notifications.";
    }
}