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

@RestController
@RequestMapping("/purchase")
public class MasterPurchaseEntryController {

    @Autowired
    private ProductService service;

    @PostMapping("/filter")
    public Page<ProductDTO> getPaginatedProductsList(@RequestBody RequestDTO request) {
        return service.getPaginatedProductsList(request);
    }

    @GetMapping("/details/{id}")
    public ProductDTO getProduct(@PathVariable int id) {
        return service.getProductById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody ProductDTO product) {
        Product res = service.saveProduct(product);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "Purchase Added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable int id, @RequestBody ProductDTO product) {
        Product res = service.updateProduct(id, product);
        return ResponseEntity.ok(ResponseUtil.success(res.getId(), "Purchase Updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Product>> deleteProduct(@PathVariable int id) {
        service.deleteProduct(id);
        return ResponseEntity.ok(ResponseUtil.success(id, "Purchase Deleted successfully"));
    }
}
