package com.prajan.FoodReduction.controller;


import com.prajan.FoodReduction.DTO.CreateDonationRequest;
import com.prajan.FoodReduction.DTO.DonationResponse;
import com.prajan.FoodReduction.userservice.DonationServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donor")
public class DonorController {

    private final DonationServiceImpl donationService;

    public DonorController(DonationServiceImpl donationService) {
        this.donationService = donationService;
    }

    @PostMapping("/donate")
    public DonationResponse createDonation(
            @RequestBody CreateDonationRequest request
    ) {
        return donationService.createDonation(request);
    }

    @GetMapping("/my")
    public List<DonationResponse> getMyDonations() {
        return donationService.getMyDonations();
    }
}
