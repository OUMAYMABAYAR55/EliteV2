package com.example.demo.springbootdb.repository;

import com.example.demo.springbootdb.Models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
