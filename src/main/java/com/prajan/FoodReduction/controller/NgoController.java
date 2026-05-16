package com.prajan.FoodReduction.controller;

import com.prajan.FoodReduction.DTO.DonationResponse;
import com.prajan.FoodReduction.userservice.NgoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ngo")
@RequiredArgsConstructor
public class NgoController {

    private final NgoService ngoService;

    /* GET ALL AVAILABLE DONATIONS */

    @GetMapping("/donations")
    public ResponseEntity<List<DonationResponse>> getAllDonations() {

        return ResponseEntity.ok(
                ngoService.getAvailableDonations()
        );
    }

    /* ACCEPT DONATION */

    @PutMapping("/accept/{donationId}")
    public ResponseEntity<String> acceptDonation(
            @PathVariable Long donationId,
            Authentication authentication
    ) {

        ngoService.acceptDonation(
                donationId,
                authentication.getName()
        );

        return ResponseEntity.ok("Donation accepted successfully");
    }

    /* GET NGO ACCEPTED DONATIONS */

    @GetMapping("/my-donations")
    public ResponseEntity<List<DonationResponse>> getMyDonations(
            Authentication authentication
    ) {
        System.out.println(authentication);
        return ResponseEntity.ok(
                ngoService.getNgoDonations(authentication.getName())
        );
    }
}