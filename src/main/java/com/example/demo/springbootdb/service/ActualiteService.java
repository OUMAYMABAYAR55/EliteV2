package com.example.demo.springbootdb.service;

import com.example.demo.springbootdb.Models.Actualite;
import com.example.demo.springbootdb.repository.ActualiteRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Setter
@Getter
@Service
public class ActualiteService {

    @Autowired
    private ActualiteRepository actualiteRepository;

    public List<Actualite> getAll() {
        return actualiteRepository.findAll();
    }

    public Optional<Actualite> getById(Long id) {
        return actualiteRepository.findById(id);
    }

    public Actualite create(Actualite actualite) {
        actualite.setDatePublication(LocalDateTime.now());
        return actualiteRepository.save(actualite);
    }

    public Actualite update(Long id, Actualite newActu) {
        Actualite old = actualiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Actualité non trouvée"));

        old.setTitre(newActu.getTitre());
        old.setContenu(newActu.getContenu());
        old.setAuteur(newActu.getAuteur());

        return actualiteRepository.save(old);
    }

    public void delete(Long id) {
        actualiteRepository.deleteById(id);
    }
}
