package com.example.demo.springbootdb.service;

import com.example.demo.springbootdb.Models.Notification;
import com.example.demo.springbootdb.Models.Utilisateur;
import com.example.demo.springbootdb.controller.NotificationController;
import com.example.demo.springbootdb.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationController notificationController; // Pour websocket

    public void envoyerNotification(Utilisateur utilisateur, String contenu) {
        Notification notif = new Notification();
        notif.setContenu(contenu);
        notif.setDateEnvoi(new Date());
        notif.setUtilisateur(utilisateur);
        notificationRepository.save(notif);

        notificationController.envoyerNotification(notif);
    }
}
