package com.prajan.FoodReduction.model;

import com.prajan.FoodReduction.enums.DonationStatus;
import com.prajan.FoodReduction.enums.FoodType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String foodName;

    private String quantity;

    private String description;

    private LocalDateTime createdAt;

    private String donorAddress ;

    private String deliveryAddress;

    private LocalDateTime expiryTime;

    private Integer servesPeople;

    @Enumerated(EnumType.STRING)
    private FoodType foodType;

    @Enumerated(EnumType.STRING)
    private DonationStatus status;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;

    @ManyToOne
    private NGO acceptedByNgo;

    @ManyToOne
    @JoinColumn(name = "volunteer_id")
    private Volunteer assignedVolunteer;
}