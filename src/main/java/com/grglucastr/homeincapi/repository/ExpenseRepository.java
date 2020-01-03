package com.grglucastr.homeincapi.repository;

import com.grglucastr.homeincapi.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT e FROM expense e WHERE e.isActive= :active AND e.paid=:paid")
    List<Expense> findAll(boolean active, boolean paid);

    List<Expense> findAllByIsActiveTrueAndPaidTrueOrderByIdAsc();

    Optional<Expense> findByIdAndIsActiveTrue(Long id);
}
