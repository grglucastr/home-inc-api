package com.grglucastr.homeincapi.repository;

import com.grglucastr.homeincapi.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
