package com.prajan.FoodReduction.userservice;

import com.prajan.FoodReduction.DTO.CreateDonationRequest;
import com.prajan.FoodReduction.DTO.DonationResponse;

import java.util.List;

public interface DonationService {

    DonationResponse createDonation(CreateDonationRequest request);

    List<DonationResponse> getMyDonations();
}