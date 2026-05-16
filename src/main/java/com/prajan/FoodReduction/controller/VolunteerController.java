package com.prajan.FoodReduction.controller;


import com.prajan.FoodReduction.DTO.DonationResponse;
import com.prajan.FoodReduction.userservice.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volunteer")
@RequiredArgsConstructor
public class VolunteerController {

    private final VolunteerService volunteerService;

    // set volunteer available/not available
    @PutMapping("/availability")
    public String updateAvailability(
            @RequestParam boolean available
    ) {

        volunteerService.updateAvailability(available);

        return available
                ? "Volunteer is now AVAILABLE"
                : "Volunteer is now NOT AVAILABLE";
    }

    // assigned donations
    @GetMapping("/assigned")
    public List<DonationResponse> getAssignedDonations() {

        return volunteerService.getAssignedDonations();
    }

    // completed donations
    @GetMapping("/completed")
    public List<DonationResponse> getCompletedDonations() {

        return volunteerService.getCompletedDonations();
    }

    // single donation details
    @GetMapping("/donation/{id}")
    public DonationResponse getDonationDetails(
            @PathVariable Long id
    ) {

        return volunteerService.getDonationDetails(id);
    }

    // mark picked up
    @PutMapping("/pickup/{id}")
    public String markPickedUp(
            @PathVariable Long id
    ) {

        volunteerService.markPickedUp(id);

        return "Donation picked up successfully";
    }

    // mark completed
    @PutMapping("/complete/{id}")
    public String markCompleted(
            @PathVariable Long id
    ) {

        volunteerService.markCompleted(id);

        return "Donation completed successfully";
    }
}