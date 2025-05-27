package com.sandipsky.inventory_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "document_number")
public class DocumentNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String module;  

    private String prefix; 

    private int startNumber;   

    private int endNumber;   

    private int length;       

    private String description;   
}
