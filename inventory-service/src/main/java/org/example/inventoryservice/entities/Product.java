package org.example.inventoryservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Builder
public class Product {
    @Id
    private String id;
    private String name;
    private double price;
    private int quantity;
}
