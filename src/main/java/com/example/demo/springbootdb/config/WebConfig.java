package com.example.demo.springbootdb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 👇 Sert les fichiers depuis le dossier /uploads/images/
        registry.addResourceHandler("/uploads/images/**")
                .addResourceLocations("file:" + System.getProperty("user.dir") + "/uploads/images/")
                .setCachePeriod(3600); // Optionnel : cache d'une heure
    }
}
