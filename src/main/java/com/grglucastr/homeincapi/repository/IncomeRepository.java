package com.grglucastr.homeincapi.repository;

import com.grglucastr.homeincapi.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}
