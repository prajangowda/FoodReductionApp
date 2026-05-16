package com.prajan.FoodReduction.repository;

import com.prajan.FoodReduction.enums.DonationStatus;
import com.prajan.FoodReduction.model.Donation;
import com.prajan.FoodReduction.model.NGO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByDonorId(Long donorId);
    List<Donation> findByStatus(DonationStatus status);
    List<Donation> findByAcceptedByNgo(NGO ngo);
    List<Donation> findByAssignedVolunteerIdAndStatus(
            Long volunteerId,
            DonationStatus status
    );

    List<Donation> findByAssignedVolunteerIdAndStatusNot(
            Long volunteerId,
            DonationStatus status
    );
}