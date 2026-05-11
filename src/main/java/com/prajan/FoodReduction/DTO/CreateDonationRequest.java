package com.prajan.FoodReduction.DTO;


import com.prajan.FoodReduction.enums.FoodType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateDonationRequest {

    private String foodName;

    private String quantity;

    private String description;

    private LocalDateTime expiryTime;

    private Integer servesPeople;

    private FoodType foodType;
}