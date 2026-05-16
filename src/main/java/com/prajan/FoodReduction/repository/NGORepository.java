package com.prajan.FoodReduction.repository;

import com.prajan.FoodReduction.model.NGO;
import com.prajan.FoodReduction.model.UserIn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NGORepository extends JpaRepository<NGO, Long> {

    Optional<NGO> findByUser(UserIn user);
}