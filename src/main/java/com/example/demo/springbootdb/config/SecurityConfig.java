package com.example.demo.springbootdb.config;

import com.example.demo.springbootdb.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtFilter jwtFilter, UserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Add CORS configuration
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // 🔓 Auth public
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/parent/**").hasAnyRole("PARENT", "ADMIN") // 👨‍👩‍👧‍👦 Parent
                        .requestMatchers("/api/utilisateurs/**").hasAnyRole("ADMIN", "PARENT", "MEMBRE")
                        .requestMatchers("/api/certification/assign/**").hasAnyAuthority("ADMIN", "GERANT", "SECRETAIRE")
                        .requestMatchers("/api/events/**").hasAnyAuthority("PARENT", "ROLE_GERANT", "SECRETAIRE")  // <-- Ajout ici
                        .requestMatchers("/api/participations/**").hasAnyAuthority("PARENT", "ADMIN")
                        .requestMatchers("/api/email/**").permitAll()
                        .requestMatchers("/uploads/images/**").permitAll()
                        .requestMatchers("/api/admin/evenement/image/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow your Angular frontend
        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:4200"));

        // Allow all HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // Allow all headers
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // Allow credentials (important for JWT tokens)
        configuration.setAllowCredentials(true);

        // Cache preflight response for 1 hour
        configuration.setMaxAge(3600L);

        // Apply CORS configuration to all API endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);

        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}