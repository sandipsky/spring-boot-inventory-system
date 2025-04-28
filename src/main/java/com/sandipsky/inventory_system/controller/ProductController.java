package com.sandipsky.inventory_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.sandipsky.inventory_system.dto.ProductDTO;
import com.sandipsky.inventory_system.dto.filter.RequestDTO;
import com.sandipsky.inventory_system.service.ProductService;

import org.springframework.data.domain.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping()
    public List<ProductDTO> getProducts() {
        return service.getProducts();
    }

    @PostMapping("/view")
    public Page<ProductDTO> getPaginatedProductsList(@RequestBody RequestDTO request) {
        return service.getPaginatedProductsList(request);
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable int id) {
        return service.getProductById(id);
    }

    @PostMapping()
    public void createProduct(@RequestBody ProductDTO product) {
        service.saveProduct(product);
    }

    @PutMapping("/{id}")
    public void updateProduct(@PathVariable int id, @RequestBody ProductDTO product) {
        service.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        service.deleteProduct(id);
    }
}
