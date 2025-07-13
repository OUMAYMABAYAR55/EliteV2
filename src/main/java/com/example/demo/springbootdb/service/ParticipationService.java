package com.example.demo.springbootdb.service;

import com.example.demo.springbootdb.Models.Participation;
import com.example.demo.springbootdb.repository.ParticipationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParticipationService {

    @Autowired
    private ParticipationRepository participationRepository;

    public List<Participation> findAll() {
        return participationRepository.findAll();
    }

    public Optional<Participation> findById(long id) {
        return participationRepository.findById(id);
    }

    public Participation save(Participation participation) {
        return participationRepository.save(participation);
    }

    public void deleteById(Long id) {
        participationRepository.deleteById(id);
    }
}
