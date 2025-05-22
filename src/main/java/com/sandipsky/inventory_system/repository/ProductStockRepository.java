package com.sandipsky.inventory_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.sandipsky.inventory_system.entity.ProductStock;

public interface ProductStockRepository
        extends JpaRepository<ProductStock, Integer>, JpaSpecificationExecutor<ProductStock> {

    ProductStock findByProductId(Integer productId);
}
