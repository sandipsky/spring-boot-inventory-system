package com.sandipsky.inventory_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sandipsky.inventory_system.entity.Product;
import com.sandipsky.inventory_system.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping()
    public List<Product> getProducts() {
        return service.getProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable int id) {
        return service.getProductById(id);
    }

    @PostMapping()
    public Product createProduct(@RequestBody Product product) {
        return service.saveProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
        return service.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        service.deleteProduct(id);
    }
}
