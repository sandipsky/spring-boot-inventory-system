package com.sandipsky.inventory_system.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;
    private String message;
    @JsonProperty("post_data_id")
    private int data;
    private List<String> errors;
    private int errorCode;
    private long timestamp;
}