package com.sandipsky.inventory_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.inventory_system.entity.Category;
import com.sandipsky.inventory_system.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category saveCategory(Category category) {
        return repository.save(category);
    }

    public List<Category> getCategorys() {
        return repository.findAll();
    }

    public Category getCategoryById(int id) {
        return repository.findById(id).get();
    }

    public Category updateCategory(int id, Category category) {
        Category existing = repository.findById(id).get();
        existing.setName(category.getName());
        existing.setActive(category.isActive());
        return repository.save(existing);
    }

    public void deleteCategory(int id) {
        repository.deleteById(id);
    }
}
