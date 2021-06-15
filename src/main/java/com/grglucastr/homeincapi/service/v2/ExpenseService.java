package com.grglucastr.homeincapi.service.v2;

import com.grglucastr.homeincapi.model.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {

    Expense save(Expense expense);

    List<Expense> findAll();

    Optional<Expense> findById(Long expenseId);

    List<Expense> findByMonth(int month);

    List<Expense> findByMonthAndPaid(int month, boolean paid);
}
