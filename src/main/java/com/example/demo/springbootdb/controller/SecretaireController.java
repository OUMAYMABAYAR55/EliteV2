package com.example.demo.springbootdb.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/secretaire")
public class SecretaireController {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "Bienvenue secretaire";
    }

}
