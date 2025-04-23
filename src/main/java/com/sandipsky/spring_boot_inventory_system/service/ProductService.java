package com.sandipsky.spring_boot_inventory_system.service;

import com.sandipsky.spring_boot_inventory_system.entity.Product;
import com.sandipsky.spring_boot_inventory_system.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public Product getProductById(int id) {
        return repository.findById(id).get();
    }

    public Product updateProduct(int id, Product product) {
        Product existing = repository.findById(id).get();
        existing.setName(product.getName());
        existing.setCostPrice(product.getCostPrice());
        return repository.save(existing);
    }

    public void deleteProduct(int id) {
        repository.deleteById(id);
    }
}
