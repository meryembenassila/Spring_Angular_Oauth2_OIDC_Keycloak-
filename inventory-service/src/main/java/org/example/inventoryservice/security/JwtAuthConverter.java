package org.example.inventoryservice.security;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component

public class JwtAuthConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    // Convertisseur fourni par Spring Security.
    // Il extrait les autorités présentes dans les claims standards du JWT,
    // notamment "scope" ou "scp".
    //
    // Exemple :
    // "scope": "email profile"
    // devient :
    // SCOPE_email et SCOPE_profile.
    //
    // Attention : il ne récupère pas automatiquement les rôles
    // contenus dans realm_access.roles de Keycloak.
    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter=new JwtGrantedAuthoritiesConverter();
    //convert()  :
    // - reçoit un objet Jwt ;
    // - retourne un objet AbstractAuthenticationToken.
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        // Fusionne deux collections d'autorités :
        //
        // 1. Les autorités extraites automatiquement par Spring
        //    (scope, scp...).
        //
        // 2. Les rôles Keycloak extraits manuellement
        //    depuis realm_access.roles.
        //
        // Stream.concat() fusionne les deux flux.
        // collect(Collectors.toSet()) rassemble les résultats
        // dans un Set pour éviter les doublons.
        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());
        // Construit l'objet d'authentification Spring Security.
        // - jwt : le JWT original.
        // - authorities : les rôles et autorités de l'utilisateur.
        // - preferred_username : le nom de l'utilisateur.
        return new JwtAuthenticationToken(
                jwt,
                authorities,
                jwt.getClaimAsString("preferred_username")
        );
    }


    // Méthode personnalisée permettant d'extraire
    // les rôles placés par Keycloak dans realm_access.roles.
    private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt) {

        Map<String, Object> realmAccess;
        Collection<String> roles;

        // Vérifie si le claim realm_access existe dans le JWT.
        // S'il n'existe pas, on retourne une collection vide.
        if (jwt.getClaim("realm_access") == null) {
            return Set.of();
        }

        // Récupère le contenu du claim realm_access.
        // Exemple :
        // {
        //     "roles": ["ADMIN", "USER"]
        // }
        realmAccess = jwt.getClaim("realm_access");

        // Récupère la liste des rôles depuis la clé "roles".
        roles = (Collection<String>) realmAccess.get("roles");

        // Transforme chaque rôle en SimpleGrantedAuthority.
        //
        // Exemple :
        // "ADMIN" devient new SimpleGrantedAuthority("ADMIN")
        // "USER"  devient new SimpleGrantedAuthority("USER")
        //
        // Spring Security utilise GrantedAuthority
        // pour représenter les rôles et permissions.
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toSet());
    }
}