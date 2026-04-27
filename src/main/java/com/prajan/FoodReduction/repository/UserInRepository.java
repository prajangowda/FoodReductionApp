package com.prajan.FoodReduction.repository;

import com.prajan.FoodReduction.enums.provider;
import com.prajan.FoodReduction.model.UserIn;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserInRepository extends JpaRepository<UserIn, Long> {
    Optional<UserIn> findByEmail(String email);

    Optional<UserIn> findByProviderIdAndProvider(String providerId, provider providerType);
}