package com.grglucastr.homeincapi.service.v2;

import com.grglucastr.homeincapi.model.Income;

import java.util.List;
import java.util.Optional;

public interface IncomeService {

    Income save(Income income);

    List<Income> findAll();

    Optional<Income> findById(Long incomeId);

    Optional<Income> findByDateRange(int year, int month);
}
