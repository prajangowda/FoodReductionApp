package com.prajan.FoodReduction.userservice;

import com.prajan.FoodReduction.DTO.DonationResponse;
import com.prajan.FoodReduction.enums.DonationStatus;
import com.prajan.FoodReduction.model.Donation;
import com.prajan.FoodReduction.model.NGO;
import com.prajan.FoodReduction.model.UserIn;
import com.prajan.FoodReduction.model.Volunteer;
import com.prajan.FoodReduction.repository.DonationRepository;
import com.prajan.FoodReduction.repository.NGORepository;
import com.prajan.FoodReduction.repository.UserInRepository;

import com.prajan.FoodReduction.repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NgoService {

    private final DonationRepository donationRepository;
    private final UserInRepository userRepository;
    private final NGORepository ngoRepository;
    private final VolunteerRepository volunteerRepository;
    private final EmailService emailService;

    /* GET AVAILABLE DONATIONS */

    public List<DonationResponse> getAvailableDonations() {

        List<Donation> donations =
                donationRepository.findByStatus(DonationStatus.AVAILABLE);

        return donations.stream()
                .map(this::mapToResponse)
                .toList();
    }

    /* ACCEPT DONATION */

    public void acceptDonation(Long donationId, String email) {

        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() ->
                        new RuntimeException("Donation not found"));

        if (!donation.getStatus().equals(DonationStatus.AVAILABLE)) {
            throw new RuntimeException("Donation already accepted");
        }

        UserIn user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        NGO ngo = ngoRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("NGO not found"));

        donation.setStatus(DonationStatus.ACCEPTED);
        donation.setDeliveryAddress(ngo.getAddress());
        donation.setAcceptedByNgo(ngo);

        // FIND NEARBY VOLUNTEERS
        List<Volunteer> volunteers =
                volunteerRepository.findByAvailableTrue();

        Volunteer nearestVolunteer = null;
        double minDistance = Double.MAX_VALUE;

        for (Volunteer volunteer : volunteers) {

            double distance = DistanceUtil.calculateDistance(
                    donation.getDonor().getLatitude(),
                    donation.getDonor().getLongitude(),
                    volunteer.getLatitude(),
                    volunteer.getLongitude()
            );

            // within 6 km radius
            if (distance <= 6 && distance < minDistance) {
                minDistance = distance;
                nearestVolunteer = volunteer;
            }
        }

        // ASSIGN VOLUNTEER
        if (nearestVolunteer != null) {

            donation.setAssignedVolunteer(nearestVolunteer);

            emailService.sendVolunteerAssignmentEmail(
                    nearestVolunteer.getUser().getEmail(),
                    donation,
                    nearestVolunteer
            );

            nearestVolunteer.setAvailable(false);

            volunteerRepository.save(nearestVolunteer);
        }

        donationRepository.save(donation);
    }

    /* GET NGO ACCEPTED DONATIONS */

    public List<DonationResponse> getNgoDonations(String email) {

        UserIn user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        NGO ngo = ngoRepository.findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("NGO not found"));

        List<Donation> donations =
                donationRepository.findByAcceptedByNgo(ngo);

        return donations.stream()
                .map(this::mapToResponse)
                .toList();
    }

    /* DTO MAPPING */

    private DonationResponse mapToResponse(Donation donation) {

        return DonationResponse.builder()
                .id(donation.getId())
                .foodType(donation.getFoodType())
                .quantity(donation.getQuantity())
                .status(donation.getStatus())
                .donorName(donation.getDonor().getName())
                .foodName(donation.getFoodName())
                .servesPeople(donation.getServesPeople())
                .donorAddress(donation.getDonorAddress())
                .build();
    }
}
