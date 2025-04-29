package com.sandipsky.inventory_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sandipsky.inventory_system.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, int id);
}

