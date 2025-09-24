package com.example.demo.springbootdb.service;

import com.example.demo.springbootdb.Models.Event;
import com.example.demo.springbootdb.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public void deleteById(Long id) {
        eventRepository.deleteById(id);
    }

    public List<Event> findByParentId(Long parentId) {
        return eventRepository.findByParentId(parentId);
    }

    public List<Event> findByType(String type) {
        return eventRepository.findByType(type);
    }
}