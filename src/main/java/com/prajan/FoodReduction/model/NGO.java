package com.prajan.FoodReduction.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NGO {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "id")
    private UserIn user;

    // Basic Info
    private String ngoName;
    private String contactPersonName;
    private String ngophone;
    // Location (VERY IMPORTANT for matching food)
    private String address;

    private Double latitude;
    private Double longitude;

    // Capacity / Preference


    // Status
    private boolean isApproved; // admin approval
}
