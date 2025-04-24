package com.sandipsky.inventory_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.inventory_system.entity.Product;
import com.sandipsky.inventory_system.repository.ProductRepository;

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
        existing.setCode(product.getCode());
        existing.setActive(product.isActive());
        existing.setServiceItem(product.isServiceItem());
        existing.setPurchasable(product.isPurchasable());
        existing.setSellable(product.isSellable());
        existing.setCostPrice(product.getCostPrice());
        existing.setSellingPrice(product.getSellingPrice());
        existing.setMrp(product.getMrp());
        return repository.save(existing);
    }

    public void deleteProduct(int id) {
        repository.deleteById(id);
    }
}
