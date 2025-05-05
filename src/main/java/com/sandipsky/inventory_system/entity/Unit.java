package com.sandipsky.inventory_system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

// import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @JsonProperty("is_active")
    private boolean isActive;

    // @OneToMany(mappedBy = "unit")
    // private List<Product> products;
}