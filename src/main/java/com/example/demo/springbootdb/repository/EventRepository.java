package com.example.demo.springbootdb.repository;

import com.example.demo.springbootdb.Models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByParentId(Long parentId);
    List<Event> findByType(String type);
}