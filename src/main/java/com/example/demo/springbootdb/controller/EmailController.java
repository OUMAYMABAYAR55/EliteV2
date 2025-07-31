package com.example.demo.springbootdb.controller;

import com.example.demo.springbootdb.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    /**
     * 🔹 Envoyer un email de réinitialisation avec query params (ex: GET ou POST)
     * Exemple : POST /api/email/send-reset?to=test@gmail.com&link=http://localhost/reset/123
     */
    @PostMapping("/send-reset")
    public String sendResetPasswordEmail(
            @RequestParam String to,
            @RequestParam String link
    ) {
        emailService.sendResetPasswordEmail(to, link);
        return "Email de réinitialisation envoyé à " + to;
    }

    /**
     * 🔹 Envoyer un email de réinitialisation avec JSON dans le corps
     * Exemple JSON body :
     * {
     *   "to": "test@gmail.com",
     *   "link": "http://localhost:4200/reset/123"
     * }
     */
    @PostMapping("/send-reset-json")
    public String sendResetPasswordEmailJson(@RequestBody Map<String, String> body) {
        String to = body.get("to");
        String link = body.get("link");

        if(to == null || link == null) {
            return "Paramètres 'to' et 'link' sont obligatoires.";
        }

        emailService.sendResetPasswordEmail(to, link);
        return "Email de réinitialisation envoyé à " + to;
    }
}
