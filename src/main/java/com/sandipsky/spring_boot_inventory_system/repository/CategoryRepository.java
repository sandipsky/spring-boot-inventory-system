package com.sandipsky.spring_boot_inventory_system.repository;

import com.sandipsky.spring_boot_inventory_system.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
}

