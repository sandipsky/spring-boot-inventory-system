package com.sandipsky.inventory_system.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PartyDTO {
    private int id;
    private String name;
    @JsonProperty("registration_number")
    private String registrationNumber;
    @JsonProperty("is_active")
    private boolean isActive;
    private String type;
    private String contact;
    private String address;
    private String email;
    private String remarks;
}