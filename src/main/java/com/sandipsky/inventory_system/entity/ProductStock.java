package com.sandipsky.inventory_system.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProductStock {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String batch;
    
    private Double costPrice;
    private Double sellingPrice;
    private Double mrp;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}