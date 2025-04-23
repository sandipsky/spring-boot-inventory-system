package com.sandipsky.spring_boot_inventory_system.controller;

import com.sandipsky.spring_boot_inventory_system.entity.Category;
import com.sandipsky.spring_boot_inventory_system.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorys")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping()
    public List<Category> getCategorys() {
        return service.getCategorys();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable int id) {
        return service.getCategoryById(id);
    }

    @PostMapping()
    public Category createCategory(@RequestBody Category category) {
        return service.saveCategory(category);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable int id, @RequestBody Category category) {
        return service.updateCategory(id, category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable int id) {
        service.deleteCategory(id);
    }
}
