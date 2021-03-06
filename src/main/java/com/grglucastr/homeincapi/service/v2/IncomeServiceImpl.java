package com.grglucastr.homeincapi.service.v2;

import com.grglucastr.homeincapi.model.Income;
import com.grglucastr.homeincapi.repository.IncomeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Income> findById(Long incomeId) {
        log.info("Finding income by id: {}", incomeId);
        return repository.findById(incomeId);
    }

    @Override
    public Optional<Income> findByDateRange(int year, int month) {
        log.info("Finding income by year: {} and month: {}", year, month);
        final LocalDate startDate = LocalDate.of(year, month, 1);
        final LocalDate endDate = startDate.plusMonths(1L).minusDays(1L);

        log.info("Start date: {}", startDate);
        log.info("End date: {}", endDate);

        return repository.findByDateRange(startDate, endDate);
    }
}
