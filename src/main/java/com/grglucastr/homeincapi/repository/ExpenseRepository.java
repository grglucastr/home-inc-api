package com.grglucastr.homeincapi.repository;

import com.grglucastr.homeincapi.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    //@Query("SELECT e FROM Expense e ")
    //List<Expense> findAllExpeses(LocalDate start, LocalDate end, boolean active, boolean paid);

    List<Expense> findAllByIsActiveTrueOrderByIdAsc();
    List<Expense> findAllByIsActiveTrueAndPaidTrueOrderByIdAsc();

    Optional<Expense> findByIdAndIsActiveTrue(Long id);
}
