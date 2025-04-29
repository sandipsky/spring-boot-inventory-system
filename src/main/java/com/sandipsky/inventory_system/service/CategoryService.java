package com.sandipsky.inventory_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.inventory_system.entity.Category;
import com.sandipsky.inventory_system.exception.DuplicateResourceException;
import com.sandipsky.inventory_system.exception.ResourceNotFoundException;
import com.sandipsky.inventory_system.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category saveCategory(Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new RuntimeException("Category name cannot be null or blank");
        }
        if (repository.existsByName(category.getName().trim())) {
            throw new DuplicateResourceException("Category with the same name already exists");
        }
        category.setName(category.getName().trim());
        return repository.save(category);
    }

    public List<Category> getCategorys() {
        return repository.findAll();
    }

    public Category getCategoryById(int id) {
        Category existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return existing;
    }

    public Category updateCategory(int id, Category category) {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new RuntimeException("Category name cannot be null or blank");
        }
        Category existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if (repository.existsByNameAndIdNot(category.getName().trim(), id)) {
            throw new DuplicateResourceException("Category with the same name already exists");
        }
        existing.setName(category.getName().trim());
        existing.setActive(category.isActive());
        return repository.save(existing);
    }

    public void deleteCategory(int id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        repository.deleteById(id);
    }
}
