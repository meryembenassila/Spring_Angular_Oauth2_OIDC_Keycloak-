package org.example.inventoryservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Configuration // indique à Spring que cette classe contient une configuration.
@EnableWebSecurity // active la configuration de Spring Security pour sécuriser les requêtes HTTP.
public class SecurityConfig {
    @Bean
    // SecurityFilterChain intercepte la requete avant d'arriver au controller
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(ar->ar.requestMatchers("/api/**","/h2-console/**").permitAll())//Autoriser toutes les requêtes HTTP dont l'URL commence par /api/, sans exiger d'authentification.
                .headers(h->h.frameOptions(f->f.disable()))
                .authorizeHttpRequests(ar->ar.anyRequest().authenticated())//Toutes les requêtes HTTP restantes doivent être effectuées par un utilisateur authentifié.
                .build();
}}
