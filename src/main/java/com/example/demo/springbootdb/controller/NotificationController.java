package com.example.demo.springbootdb.controller;
import com.example.demo.springbootdb.Models.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void envoyerNotification(Notification notification) {
        messagingTemplate.convertAndSend(
                "/topic/notifications/" + notification.getUtilisateur().getId(), // ex: /topic/notifications/3
                notification.getContenu()
        );
    }
}
