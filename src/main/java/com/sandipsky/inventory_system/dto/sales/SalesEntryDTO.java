package com.sandipsky.inventory_system.dto.sales;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesEntryDTO {
    private int id;

    @JsonProperty("master_sales_entry_id")
    private int masterSalesEntryId;

    private Double quantity;

    @JsonProperty("rate")
    private Double rate;

    @JsonProperty("product_id")
    private int productId;

    @JsonProperty("product_name")
    private String productName;
}
