package com.prajan.FoodReduction.DTO;

import com.prajan.FoodReduction.enums.Role;
import lombok.Data;

@Data
public class CompleteProfileRequest {

    private Role role;

    private String name;
    private String phone;

    private String address;
    private Double latitude;
    private Double longitude;

    // NGO only
    private String ngoName;
    private String contactPersonName;
}