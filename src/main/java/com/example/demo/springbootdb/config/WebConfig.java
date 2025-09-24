package com.example.demo.springbootdb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Chemin absolu vers le dossier uploads
        String currentDirectory = System.getProperty("user.dir");
        Path uploadPath = Paths.get(currentDirectory, "uploads", "images");

        System.out.println("📁 Dossier images configuré: " + uploadPath.toAbsolutePath());

        // Configure l'accès aux images
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath.toAbsolutePath() + "/")
                .setCachePeriod(0);

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + uploadPath.toAbsolutePath() + "/")
                .setCachePeriod(0);
    }
}