package com.sandipsky.spring_boot_inventory_system.entity;
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
    private boolean status;

    // @OneToMany(mappedBy = "unit")
    // private List<Product> products;
}