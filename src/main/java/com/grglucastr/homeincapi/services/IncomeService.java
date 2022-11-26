package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.models.Income;

import java.util.List;

public interface IncomeService {
    List<Income> listActiveIncomes(Long userId);
    Income save(Income income);
}
