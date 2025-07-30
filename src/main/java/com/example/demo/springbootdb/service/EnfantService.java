package com.example.demo.springbootdb.service;


import com.example.demo.springbootdb.repository.EnfantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnfantService {

    @Autowired
    private EnfantRepository enfantRepository;

    public long countAllEnfants() {
        return enfantRepository.count();
    }
}
