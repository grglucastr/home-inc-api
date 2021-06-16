package com.grglucastr.homeincapi.service.v2;

import com.grglucastr.homeincapi.model.Income;

import java.util.List;

public interface IncomeService {

    Income save(Income income);

    List<Income> findALl();
}
