package com.example.demo.springbootdb.repository;

import com.example.demo.springbootdb.Models.Actualite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActualiteRepository extends JpaRepository<Actualite, Long> {
}
