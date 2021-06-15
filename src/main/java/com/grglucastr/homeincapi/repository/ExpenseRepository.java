package com.grglucastr.homeincapi.repository;

import com.grglucastr.homeincapi.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT e FROM expense e WHERE e.isActive= :active AND e.paid=:paid")
    Page<Expense> findAll(boolean active, boolean paid, Pageable pageable);

    @Query("SELECT e FROM expense e WHERE EXTRACT(MONTH FROM e.dueDate) = :month")
    List<Expense> findByMonthNumber(int month);

    @Query("SELECT e FROM expense e WHERE EXTRACT(MONTH FROM e.dueDate) = :month AND e.paid = :paid")
    List<Expense> findByMonthNumberAndPaid(int month, boolean paid);

    List<Expense> findAllByIsActiveTrueAndPaidTrueOrderByIdAsc();

    Optional<Expense> findByIdAndIsActiveTrue(Long id);
}
