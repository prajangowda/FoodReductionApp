package com.prajan.FoodReduction.userservice;

import com.prajan.FoodReduction.DTO.CompleteProfileRequest;
import com.prajan.FoodReduction.enums.Role;
import com.prajan.FoodReduction.model.Donor;
import com.prajan.FoodReduction.model.NGO;
import com.prajan.FoodReduction.model.UserIn;
import com.prajan.FoodReduction.model.Volunteer;
import com.prajan.FoodReduction.repository.UserInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class RoleService {


    private final UserInRepository userRepository;

    public void completeProfile(UserIn user, CompleteProfileRequest req) {


        //  prevent re-submission
        if (user.isProfileCompleted()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Profile already completed"
            );
        }

        //  validate role
        if (req.getRole() == null || req.getRole() == Role.USER) {
            throw new RuntimeException("Invalid role selection");
        }

        //  validate location
        validateIndiaBounds(req.getLatitude(), req.getLongitude());

        user.setRole(req.getRole());

        switch (req.getRole()) {

            case DONOR -> {
                validateCommon(req);

                Donor donor = Donor.builder()
                        .user(user)
                        .name(req.getName())
                        .phone(req.getPhone())
                        .address(req.getAddress())
                        .latitude(req.getLatitude())
                        .longitude(req.getLongitude())
                        .build();

                user.setDonor(donor);

            }

            case NGO -> {
                if (req.getNgoName() == null || req.getContactPersonName() == null) {
                    throw new RuntimeException("NGO details missing");
                }

                NGO ngo = NGO.builder()
                        .user(user)
                        .ngoName(req.getNgoName())
                        .contactPersonName(req.getContactPersonName())
                        .address(req.getAddress())
                        .latitude(req.getLatitude())
                        .longitude(req.getLongitude())
                        .ngophone(req.getPhone())
                        .isApproved(false)
                        .build();

                user.setNgo(ngo);
            }

            case VOLUNTEER -> {
                validateCommon(req);

                Volunteer volunteer = Volunteer.builder()
                        .user(user)
                        .name(req.getName())
                        .phoneNumber(req.getPhone())
                        .address(req.getAddress())
                        .latitude(req.getLatitude())
                        .longitude(req.getLongitude())
                        .available(true)
                        .build();

                user.setVolunteer(volunteer);
            }
        }

        user.setProfileCompleted(true);
        userRepository.save(user);

    }

    private void validateCommon(CompleteProfileRequest req) {
        if (req.getName() == null || req.getPhone() == null) {
            throw new RuntimeException("Missing required fields");
        }
    }

    private void validateIndiaBounds(Double lat, Double lon) {
        if (lat == null || lon == null) {
            throw new RuntimeException("Location required");
        }

        if (lat < 6.5 || lat > 37.5 || lon < 68.0 || lon > 97.5) {
            throw new RuntimeException("Location must be within India");
        }
    }
}
