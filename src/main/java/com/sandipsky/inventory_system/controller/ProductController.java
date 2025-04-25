package com.sandipsky.inventory_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.sandipsky.inventory_system.dto.ApiResponse;
import com.sandipsky.inventory_system.dto.ProductDTO;
import com.sandipsky.inventory_system.dto.filter.RequestDTO;
import com.sandipsky.inventory_system.entity.Product;
import com.sandipsky.inventory_system.service.ProductService;
import com.sandipsky.inventory_system.util.ResponseUtil;

import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

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
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody ProductDTO product) {
        Product res = service.saveProduct(product);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "Product Added successfully"));
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody ProductDTO product) {
        return service.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        service.deleteProduct(id);
    }
}
