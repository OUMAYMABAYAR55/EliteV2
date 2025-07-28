package com.example.demo.springbootdb.controller;

import com.example.demo.springbootdb.Models.Certification;
import com.example.demo.springbootdb.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certification")
public class CertificationController {

    private final CertificationService certificationService;

    @Autowired
    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @GetMapping
    public List<Certification> getAll() {
        return certificationService.findAll();
    }

    @PostMapping("/assign/{idDonneur}/{idParent}")
    public Certification assignCertification(
            @PathVariable Long idDonneur,
            @PathVariable Long idParent
    ) {
        return certificationService.creerCertification(idDonneur, idParent);
    }

    @GetMapping("/parent/{idParent}")
    public List<Certification> getCertificationsByParent(@PathVariable Long idParent) {
        return certificationService.findByParentId(idParent);
    }
}
