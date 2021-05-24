package com.grglucastr.homeincapi.service.v2;

import com.grglucastr.homeincapi.model.Expense;

import java.util.List;

public interface ExpenseService {

    Expense save(Expense expense);

    List<Expense> findAll();
}
