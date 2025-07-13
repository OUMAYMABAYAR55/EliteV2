package com.example.demo.springbootdb.repository;

import com.example.demo.springbootdb.Models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
