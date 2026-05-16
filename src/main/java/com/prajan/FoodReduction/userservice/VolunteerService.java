package com.prajan.FoodReduction.userservice;


import com.prajan.FoodReduction.DTO.DonationResponse;
import com.prajan.FoodReduction.enums.DonationStatus;
import com.prajan.FoodReduction.model.*;
import com.prajan.FoodReduction.repository.DonationRepository;
import com.prajan.FoodReduction.repository.UserInRepository;
import com.prajan.FoodReduction.repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final DonationRepository donationRepository;
    private final UserInRepository userRepository;

    // update availability
    public void updateAvailability(boolean available) {

        Volunteer volunteer = getCurrentVolunteer();

        volunteer.setAvailable(available);

        volunteerRepository.save(volunteer);
    }

    // assigned donations
    public List<DonationResponse> getAssignedDonations() {

        Volunteer volunteer = getCurrentVolunteer();

        List<Donation> donations =
                donationRepository
                        .findByAssignedVolunteerIdAndStatusNot(
                                volunteer.getId(),
                                DonationStatus.DELIVERED
                        );

        return donations
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // completed donations
    public List<DonationResponse> getCompletedDonations() {

        Volunteer volunteer = getCurrentVolunteer();

        List<Donation> donations =
                donationRepository
                        .findByAssignedVolunteerIdAndStatus(
                                volunteer.getId(),
                                DonationStatus.DELIVERED
                        );

        return donations
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // donation details
    public DonationResponse getDonationDetails(Long donationId) {

        Donation donation =
                donationRepository.findById(donationId)
                        .orElseThrow(() ->
                                new RuntimeException("Donation not found")
                        );

        return mapToResponse(donation);
    }

    // pickup
    public void markPickedUp(Long donationId) {

        Donation donation =
                donationRepository.findById(donationId)
                        .orElseThrow(() ->
                                new RuntimeException("Donation not found")
                        );

        donation.setStatus(DonationStatus.PICKED_UP);

        donationRepository.save(donation);
    }

    // completed
    public void markCompleted(Long donationId) {

        Donation donation =
                donationRepository.findById(donationId)
                        .orElseThrow(() ->
                                new RuntimeException("Donation not found")
                        );

        donation.setStatus(DonationStatus.DELIVERED);

        donationRepository.save(donation);
    }

    // current volunteer helper
    private Volunteer getCurrentVolunteer() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        UserIn user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        return volunteerRepository
                .findById(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Volunteer not found")
                );
    }

    // mapper
    private DonationResponse mapToResponse(
            Donation donation
    ) {

        return DonationResponse.builder()
                .id(donation.getId())
                .foodType(donation.getFoodType())
                .quantity(donation.getQuantity())
                .servesPeople(donation.getServesPeople())
                .createdAt(donation.getCreatedAt())
                .donorAddress(donation.getDonorAddress())
                .deliveryAddress(donation.getDeliveryAddress())
                .status(donation.getStatus())
                .foodName(donation.getFoodName())
                .ngoName(donation.getAcceptedByNgo().getNgoName())
                .ngoPhone(donation.getAcceptedByNgo().getNgophone())
                .donorName(
                        donation.getDonor().getName()
                )

                .donorPhone(
                        donation.getDonor().getPhone()
                )

                .assignedVolunteerName(
                        donation.getAssignedVolunteer() != null
                                ? donation.getAssignedVolunteer()
                                .getName()
                                : null
                )



                .build();
    }
}
