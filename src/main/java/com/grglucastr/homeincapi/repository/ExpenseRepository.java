package com.grglucastr.homeincapi.repository;

import com.grglucastr.homeincapi.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT e FROM expense e WHERE e.isActive= :active AND e.paid=:paid AND (e.dueDate BETWEEN :start AND :end)")
    List<Expense> findAll(boolean active, boolean paid, LocalDate start, LocalDate end );

    List<Expense> findAllByIsActiveTrueOrderByIdAsc();
    List<Expense> findAllByIsActiveTrueAndPaidTrueOrderByIdAsc();

    Optional<Expense> findByIdAndIsActiveTrue(Long id);
}
