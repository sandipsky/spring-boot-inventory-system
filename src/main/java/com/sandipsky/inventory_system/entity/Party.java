package com.sandipsky.inventory_system.entity;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonProperty("registration_number")
    private String registrationNumber;
    @JsonProperty("is_active")
    private boolean isActive;
    private String type;
    private String contact;
    private String address;
    private String email;
    private String remarks;
}