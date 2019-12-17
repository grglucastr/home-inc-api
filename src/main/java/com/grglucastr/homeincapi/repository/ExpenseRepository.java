package com.grglucastr.homeincapi.repository;

import com.grglucastr.homeincapi.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByIsActiveTrue();
    Optional<Expense> findByIdAndIsActiveTrue(Long id);
}
