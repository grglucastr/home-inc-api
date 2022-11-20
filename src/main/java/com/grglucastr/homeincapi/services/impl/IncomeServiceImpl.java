package com.grglucastr.homeincapi.services.impl;

import com.grglucastr.homeincapi.models.Income;
import com.grglucastr.homeincapi.repositories.IncomeRepository;
import com.grglucastr.homeincapi.services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepository repository;

    @Override
    public List<Income> listActiveIncomes() {
        return repository.findAllByActiveTrue();
    }

    @Override
    public Income save(Income income) {
        return repository.save(income);
    }
}
