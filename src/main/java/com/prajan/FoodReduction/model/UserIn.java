package com.prajan.FoodReduction.model;

import com.prajan.FoodReduction.enums.Role;
import com.prajan.FoodReduction.enums.provider;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private provider provider;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean profileCompleted = false;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Donor donor;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Volunteer volunteer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private NGO ngo;

    @Override
    public String toString() {
        return "UserIn{" +
                "email='" + email + '\'' +
                ", provider=" + provider +
                ", role=" + role +
                ", profileCompleted=" + profileCompleted +
                '}';
    }
}
