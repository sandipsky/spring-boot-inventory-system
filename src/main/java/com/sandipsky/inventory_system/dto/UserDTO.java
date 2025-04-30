package com.sandipsky.inventory_system.dto;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDTO {
    private int id;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private String gender;
    private String contact;
    private boolean isActive;
}
