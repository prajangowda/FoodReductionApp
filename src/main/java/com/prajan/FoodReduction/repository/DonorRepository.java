package com.prajan.FoodReduction.repository;

import com.prajan.FoodReduction.model.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonorRepository extends JpaRepository<Donor, Long> {
}