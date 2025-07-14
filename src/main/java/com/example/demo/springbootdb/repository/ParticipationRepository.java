package com.example.demo.springbootdb.repository;

import com.example.demo.springbootdb.Models.Participation;
import com.example.demo.springbootdb.Models.Event;
import com.example.demo.springbootdb.Models.Enfant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    List<Participation> findByEvent(Event event);

    boolean existsByEnfantAndEvent(Enfant enfant, Event event);

}
