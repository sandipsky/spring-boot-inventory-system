package com.sandipsky.inventory_system.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Party {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private String registrationNumber;
    private boolean isActive;
    private String type;
    private String contact;
    private String address;
    private String email;
    private String remarks;
}