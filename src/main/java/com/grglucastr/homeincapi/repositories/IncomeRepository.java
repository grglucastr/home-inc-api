package com.grglucastr.homeincapi.repositories;

import com.grglucastr.homeincapi.models.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findAllByActiveTrue();
    Income save(Income income);
}
