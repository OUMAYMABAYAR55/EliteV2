package com.example.demo.springbootdb.controller;

import com.example.demo.springbootdb.Models.Event;
import com.example.demo.springbootdb.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAll() {
        return eventService.findAll();
    }

    @GetMapping("/parent/{idParent}")
    public List<Event> getByParent(@PathVariable Long idParent) {
        return eventService.findByParentId(idParent);
    }

    @PostMapping
    public Event create(@RequestBody Event event) {
        return eventService.save(event);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        eventService.deleteById(id);
        return "Event supprimé";
    }
}
