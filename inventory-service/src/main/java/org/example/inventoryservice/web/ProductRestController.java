package org.example.inventoryservice.web;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.example.inventoryservice.entities.Product;
import org.example.inventoryservice.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")

public class ProductRestController {
    private ProductRepository productRepository;

    @GetMapping("/products")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Product> productList(){
        return productRepository.findAll();
    }


    @GetMapping("/products/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public Product productList(@PathVariable String id){
        return productRepository.findById(id).orElse(null);
    }

    //récupérer les informations de l'utilisateur actuellement authentifié dans Spring Security.
    @GetMapping("/auth")
    public Authentication authentication(Authentication authentication){//!!!!! attention au class importé
        return authentication;
    }




}
