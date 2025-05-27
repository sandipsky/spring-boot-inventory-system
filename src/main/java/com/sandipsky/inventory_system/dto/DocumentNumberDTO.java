package com.sandipsky.inventory_system.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentNumberDTO {
    private int id;

    private String module;

    private String prefix;

    @JsonProperty("start_number")
    private int startNumber;

    @JsonProperty("end_number")
    private int endNumber;

    @JsonProperty("current_number")
    private String currentNumber;

    private int length;

    @JsonProperty("total_length")
    private int totalLength;

    private String description;
}