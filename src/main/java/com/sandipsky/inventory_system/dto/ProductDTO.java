package com.sandipsky.inventory_system.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProductDTO {
    private int id;
    private String name;
    private String code;
    private Double costPrice;
    private Double sellingPrice;
    private Double mrp;
    private int categoryId;
    private String categoryName;
    private int unitId;
    private String unitName;
    private boolean isActive;
    private boolean isPurchasable;
    private boolean isSellable;
    private boolean isServiceItem;
}
