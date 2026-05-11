package com.prajan.FoodReduction.userservice;


import com.prajan.FoodReduction.DTO.CreateDonationRequest;
import com.prajan.FoodReduction.DTO.DonationResponse;
import com.prajan.FoodReduction.enums.DonationStatus;
import com.prajan.FoodReduction.model.Donation;
import com.prajan.FoodReduction.model.Donor;
import com.prajan.FoodReduction.model.UserIn;
import com.prajan.FoodReduction.repository.DonationRepository;
import com.prajan.FoodReduction.repository.DonorRepository;
import com.prajan.FoodReduction.repository.UserInRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {

    private final DonationRepository donationRepository;
    private final UserInRepository userRepository;
    private final DonorRepository donorRepository;

    @Override
    public DonationResponse createDonation(CreateDonationRequest request) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        UserIn user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Donor donor = donorRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Donor not found"));

        Donation donation = Donation.builder()
                .foodName(request.getFoodName())
                .quantity(request.getQuantity())
                .description(request.getDescription())
                .expiryTime(request.getExpiryTime())
                .servesPeople(request.getServesPeople())
                .foodType(request.getFoodType())
                .status(DonationStatus.AVAILABLE)
                .createdAt(LocalDateTime.now())
                .donor(donor)
                .build();

        Donation saved = donationRepository.save(donation);

        return mapToResponse(saved);
    }

    @Override
    public List<DonationResponse> getMyDonations() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        UserIn user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Donation> donations =
                donationRepository.findByDonorId(user.getId());

        return donations.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private DonationResponse mapToResponse(Donation donation) {

        return DonationResponse.builder()
                .id(donation.getId())
                .foodName(donation.getFoodName())
                .quantity(donation.getQuantity())
                .description(donation.getDescription())
                .expiryTime(donation.getExpiryTime())
                .servesPeople(donation.getServesPeople())
                .foodType(donation.getFoodType())
                .status(donation.getStatus())
                .createdAt(donation.getCreatedAt())
                .build();
    }
}
