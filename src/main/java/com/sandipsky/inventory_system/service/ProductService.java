package com.sandipsky.inventory_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sandipsky.inventory_system.dto.ProductDTO;
import com.sandipsky.inventory_system.dto.filter.RequestDTO;
import com.sandipsky.inventory_system.entity.Category;
import com.sandipsky.inventory_system.entity.Product;
import com.sandipsky.inventory_system.entity.Unit;
import com.sandipsky.inventory_system.exception.DuplicateResourceException;
import com.sandipsky.inventory_system.exception.ResourceNotFoundException;
import com.sandipsky.inventory_system.repository.CategoryRepository;
import com.sandipsky.inventory_system.repository.ProductRepository;
import com.sandipsky.inventory_system.repository.UnitRepository;
import com.sandipsky.inventory_system.util.ResponseUtil;
import com.sandipsky.inventory_system.util.SpecificationBuilder;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

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

    public void saveProduct(ProductDTO dto) {
        if (repository.existsByName(dto.getName())) {
            throw new DuplicateResourceException("Product with the same name already exists");
        }

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
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Unit unit = unitRepository.findById(dto.getUnitId())
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found"));

        product.setCategory(category);
        product.setUnit(unit);
        repository.save(product);
        ResponseEntity.ok(ResponseUtil.success(product.getId(), "Product Added successfully"));
    }

    public Page<ProductDTO> getPaginatedProductsList(RequestDTO request) {
        Pageable pageable = PageRequest.of(
                request.getPagination() != null ? request.getPagination().getPageIndex() : 0,
                request.getPagination() != null ? request.getPagination().getPageSize() : 25,
                specBuilder.buildSort(request.getSortDTO()));

        Specification<Product> spec = specBuilder.buildSpecification(request.getFilter());
        Page<Product> productPage = repository.findAll(spec, pageable);
        return productPage.map(this::mapToDTO);
    }

    public List<ProductDTO> getProducts() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    public ProductDTO getProductById(int id) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return mapToDTO(product);
    }

    public void updateProduct(int id, ProductDTO product) {
        Product existing = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (repository.existsByName(product.getName())) {
            throw new DuplicateResourceException("Product with the same name already exists");
        }

        Category category = categoryRepository.findById(product.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Unit unit = unitRepository.findById(product.getUnitId())
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found"));

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
        repository.save(existing);
        ResponseEntity.ok(ResponseUtil.success(product.getId(), "Product Updated successfully"));
    }

    public void deleteProduct(int id) {
        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        repository.deleteById(id);
    }

    private ProductDTO mapToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCode(product.getCode());
        dto.setCostPrice(product.getCostPrice());
        dto.setSellingPrice(product.getSellingPrice());
        dto.setMrp(product.getMrp());
        dto.setActive(product.isActive());
        dto.setPurchasable(product.isPurchasable());
        dto.setSellable(product.isSellable());
        dto.setServiceItem(product.isServiceItem());
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        dto.setCategoryName(product.getCategory() != null ? product.getCategory().getName() : null);
        dto.setUnitId(product.getUnit() != null ? product.getUnit().getId() : null);
        dto.setUnitName(product.getUnit() != null ? product.getUnit().getName() : null);
        return dto;
    }
}
