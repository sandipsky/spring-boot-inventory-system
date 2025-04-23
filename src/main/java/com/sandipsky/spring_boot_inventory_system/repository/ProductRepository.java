package com.sandipsky.spring_boot_inventory_system.repository;

import com.sandipsky.spring_boot_inventory_system.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    
}

