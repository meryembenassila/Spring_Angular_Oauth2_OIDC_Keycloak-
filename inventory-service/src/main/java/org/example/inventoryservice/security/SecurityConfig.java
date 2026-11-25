package org.example.inventoryservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration // indique à Spring que cette classe contient une configuration.
@EnableWebSecurity // active la configuration de Spring Security pour sécuriser les requêtes HTTP.
@EnableMethodSecurity (prePostEnabled = true ) //permettre d'utilser annotation @preautorize dans les colntrollers
public class SecurityConfig {

    @Bean
    // SecurityFilterChain intercepte la requete avant d'arriver au controller
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtAuthConverter jwtAuthConverter) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
                .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
               // .authorizeHttpRequests(ar->ar.requestMatchers("/api/**","/h2-console/**").permitAll())//Autoriser toutes les requêtes HTTP dont l'URL commence par /api/, sans exiger d'authentification.
                .authorizeHttpRequests(ar->ar.requestMatchers("/h2-console/**").permitAll())

                //.authorizeHttpRequests(ar -> ar.requestMatchers("/api/products/**").hasAuthority("ADMIN"))//protéger les endpoints qui commencent par /api/products/ en exigeant que l'utilisateur possède l'autorité ADMIN
                //au lieu de cette instruction on a  utilser @preAthorize a chaque methode

                .authorizeHttpRequests(ar->ar.anyRequest().authenticated())//Toutes les requêtes HTTP restantes doivent être effectuées par un utilisateur authentifié.
                .headers(h->h.frameOptions(f->f.disable()))
                // .oauth2ResourceServer(o2->o2.jwt(Customizer.withDefaults()))
                //configure Spring Security comme Resource Server utilisant JWT avec la config par defaut
                .oauth2ResourceServer(o2->o2.jwt(jt->jt.jwtAuthenticationConverter(jwtAuthConverter)))
                //configure Spring Security comme Resource Server utilisant JWT en utilsant la  classe personnalisée JwtAuthConverter pour convertir le JWT en objet d'authentification.
                        .build();
}

 //au lieu de @crossorigin
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;}
}

