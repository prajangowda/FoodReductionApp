package com.prajan.FoodReduction.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @JoinColumn(name = "id")
    private UserIn user;

    private String name;
    private String phone;

    // 📍 Location fields
    private String address;      // readable address
    private Double latitude;     // for distance calculation
    private Double longitude;
}
