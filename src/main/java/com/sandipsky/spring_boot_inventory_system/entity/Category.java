package com.sandipsky.spring_boot_inventory_system.entity;
// import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;
    private boolean status;

    // @OneToMany(mappedBy = "category")
    // private List<Product> products;
}