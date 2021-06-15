package com.grglucastr.homeincapi.service.v2;

import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.repository.ExpenseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ExpenseServiceImpl implements ExpenseService{

    private ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public Expense save(Expense expense) {
        log.info("Object expense request to be persisted: {}", expense);
        return expenseRepository.save(expense);
    }

    @Override
    public List<Expense> findAll() {
        log.info("Listing all expenses");
        return expenseRepository.findAll();
    }

    @Override
    public Optional<Expense> findById(Long expenseId) {
        log.info("Finding expense with id: {}", expenseId);
        return expenseRepository.findById(expenseId);
    }

    @Override
    public List<Expense> findByMonth(int month) {
        log.info("Fetching all expenses for the month: {}", month);
        return expenseRepository.findByMonthNumber(month);
    }

    @Override
    public List<Expense> findByMonthAndPaidValue(int month, boolean paid) {
        log.info("Fetching all expenses for the month={} and paid={}", month, paid);
        return expenseRepository.findByMonthNumberAndPaid(month, paid);
    }
}

