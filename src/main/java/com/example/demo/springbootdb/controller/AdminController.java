package com.example.demo.springbootdb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/api/admin")

public class AdminController {

        @GetMapping("/dashboard1")
        public String dashboard1() {
            return "Bienvenue ADMIN";
        }
        @GetMapping("/dashboard2")
    public String dashboard2() {
        return "Bienvenue ADMIN";
    }

}
