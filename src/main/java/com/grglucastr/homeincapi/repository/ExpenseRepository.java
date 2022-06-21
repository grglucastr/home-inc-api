package com.grglucastr.homeincapi.repository;

import com.grglucastr.homeincapi.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT e FROM expense e WHERE e.isActive= :active AND e.paid=:paid ORDER BY e.id ASC")
    Page<Expense> findAll(boolean active, boolean paid, Pageable pageable);

    @Query("SELECT e FROM expense e WHERE EXTRACT(YEAR FROM e.dueDate) = :year AND EXTRACT(MONTH FROM e.dueDate) = :month")
    List<Expense> findByMonthAndYearPaid(int year, int month);

    @Query("SELECT e FROM expense e WHERE EXTRACT(YEAR FROM e.dueDate) = :year AND EXTRACT(MONTH FROM e.dueDate) = :month AND e.paid = :paid")
    List<Expense> findByMonthAndYearPaid(int year, int month, boolean paid);

    List<Expense> findAllByIsActiveTrueAndPaidTrueOrderByIdAsc();

    Optional<Expense> findByIdAndIsActiveTrue(Long id);

    @Query("SELECT DISTINCT EXTRACT(YEAR FROM e.dueDate) AS year FROM expense e ORDER BY year DESC")
    List<Integer> fetchExpenseYears();

    @Query("SELECT DISTINCT EXTRACT(MONTH FROM e.dueDate) AS month FROM expense e WHERE EXTRACT (YEAR FROM e.dueDate) = :year ORDER BY month DESC")
    List<Integer> fetchExpenseMonths(int year);
}
