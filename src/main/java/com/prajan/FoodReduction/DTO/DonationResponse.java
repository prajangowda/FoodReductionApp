package com.prajan.FoodReduction.DTO;

import com.prajan.FoodReduction.enums.DonationStatus;
import com.prajan.FoodReduction.enums.FoodType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DonationResponse {

    private Long id;

    private String foodName;

    private String quantity;

    private String description;

    private LocalDateTime expiryTime;

    private Integer servesPeople;

    private FoodType foodType;

    private DonationStatus status;

    private LocalDateTime createdAt;
}
