package com.example.demo.springbootdb.controller;

import com.example.demo.springbootdb.Models.Certificat;
import com.example.demo.springbootdb.service.CertificatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificat")
public class CertificatController {

    @Autowired
    private CertificatService certificatService;

    // GET : liste de tous les certificats
    @GetMapping
    public List<Certificat> getAll() {
        return certificatService.findAll();
    }

    // POST : créer un certificat si conditions respectées
    @PostMapping("/assign/{idDonneur}/{idParent}")
    public Certificat assignCertificat(
            @PathVariable Long idDonneur,
            @PathVariable Long idParent
    ) {
        return certificatService.creerCertificat(idDonneur, idParent);
    }

    // GET : tous les certificats d’un parent
    @GetMapping("/parent/{idParent}")
    public List<Certificat> getCertificatsByParent(@PathVariable Long idParent) {
        return certificatService.findByParentId(idParent);
    }
}
