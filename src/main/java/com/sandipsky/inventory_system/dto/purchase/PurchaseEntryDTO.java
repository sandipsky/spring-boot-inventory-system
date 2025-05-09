package com.sandipsky.inventory_system.dto.purchase;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseEntryDTO {
    private int id;

    @JsonProperty("master_purchase_id")
    private int masterPurchaseId;

    private String batch;

    @JsonProperty("cost_price")
    private Double costPrice;

    @JsonProperty("selling_price")
    private Double sellingPrice;
    
    private Double mrp;
}
