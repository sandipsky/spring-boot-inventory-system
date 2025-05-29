package com.sandipsky.inventory_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "sales_entry")
public class SalesEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int masterSalesEntryId;

    private Double quantity;

    private Double rate;
   
    private Double mrp;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}