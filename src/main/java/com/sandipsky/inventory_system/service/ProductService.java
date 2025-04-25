package com.sandipsky.inventory_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.inventory_system.dto.ProductDTO;
import com.sandipsky.inventory_system.dto.filter.RequestDTO;
import com.sandipsky.inventory_system.entity.Category;
import com.sandipsky.inventory_system.entity.Product;
import com.sandipsky.inventory_system.entity.Unit;
import com.sandipsky.inventory_system.repository.CategoryRepository;
import com.sandipsky.inventory_system.repository.ProductRepository;
import com.sandipsky.inventory_system.repository.UnitRepository;
import com.sandipsky.inventory_system.util.SpecificationBuilder;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UnitRepository unitRepository;

    private final SpecificationBuilder<Product> specBuilder = new SpecificationBuilder<>();

    public Product saveProduct(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setCode(dto.getCode());
        product.setCostPrice(dto.getCostPrice());
        product.setSellingPrice(dto.getSellingPrice());
        product.setMrp(dto.getMrp());
        product.setActive(dto.isActive());
        product.setPurchasable(dto.isPurchasable());
        product.setSellable(dto.isSellable());
        product.setServiceItem(dto.isServiceItem());

        // Set the Category and Unit based on the IDs
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Unit unit = unitRepository.findById(dto.getUnitId())
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        product.setCategory(category);
        product.setUnit(unit);
        return repository.save(product);
    }

    public Page<Product> getPaginatedProductsList(RequestDTO request) {
        Pageable pageable = PageRequest.of(
                request.getPagination() != null ? request.getPagination().getPageIndex() : 0,
                request.getPagination() != null ? request.getPagination().getPageSize() : 25,
                specBuilder.buildSort(request.getSortDTO()));

        Specification<Product> spec = specBuilder.buildSpecification(request.getFilter());
        Page<Product> page = repository.findAll(spec, pageable);
        return page;
    }

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public Product getProductById(int id) {
        return repository.findById(id).get();
    }

    public Product updateProduct(int id, ProductDTO product) {
        Product existing = repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        Category category = categoryRepository.findById(product.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Unit unit = unitRepository.findById(product.getUnitId())
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        existing.setName(product.getName());
        existing.setCode(product.getCode());
        existing.setActive(product.isActive());
        existing.setServiceItem(product.isServiceItem());
        existing.setPurchasable(product.isPurchasable());
        existing.setSellable(product.isSellable());
        existing.setCostPrice(product.getCostPrice());
        existing.setSellingPrice(product.getSellingPrice());
        existing.setMrp(product.getMrp());
        existing.setCategory(category);
        existing.setUnit(unit);
        return repository.save(existing);
    }

    public void deleteProduct(int id) {
        repository.deleteById(id);
    }
}
