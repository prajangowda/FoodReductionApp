package com.prajan.FoodReduction.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Donor {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserIn user;

    private String name;
    private String phone;

    // 📍 Location fields
    private String address;      // readable address
    private Double latitude;     // for distance calculation
    private Double longitude;
}
