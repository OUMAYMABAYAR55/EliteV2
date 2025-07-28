package com.example.demo.springbootdb.repository;

import com.example.demo.springbootdb.Models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    // Ajoute cette méthode pour récupérer les events par parent
    List<Event> findByParentId(Long parentId);
}
