package com.grglucastr.homeincapi.service.v2;

import com.grglucastr.homeincapi.model.Income;
import com.grglucastr.homeincapi.repository.IncomeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class IncomeServiceImpl implements IncomeService {

    @Autowired
    private IncomeRepository repository;

    @Override
    public Income save(Income income) {
        log.info("Object income is being persisted: {}", income);
        return repository.save(income);
    }

    @Override
    public List<Income> findAll() {
        log.info("Listing all incomes");
        return repository.findAll();
    }
}
