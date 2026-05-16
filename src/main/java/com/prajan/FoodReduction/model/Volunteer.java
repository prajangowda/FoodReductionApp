package com.prajan.FoodReduction.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Volunteer {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JsonIgnore
    @JoinColumn(name = "id")
    private UserIn user;

    // Basic Info
    private String name;
    private String phoneNumber;

    // Location (VERY IMPORTANT)
    private Double latitude;
    private Double longitude;
    private String address;

    // Status
    private boolean available;

}
