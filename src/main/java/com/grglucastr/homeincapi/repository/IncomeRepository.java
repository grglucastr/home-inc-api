package com.grglucastr.homeincapi.repository;

import com.grglucastr.homeincapi.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Override
    @Query("SELECT i FROM income i ORDER BY i.id ASC")
    List<Income> findAll();


    @Query("SELECT i FROM income i WHERE i.accountingPeriodStart <= :startDate " +
            "AND (i.accountingPeriodEnd >= :endDate OR i.accountingPeriodEnd IS NULL)")
    Optional<Income> findByDateRange(LocalDate startDate, LocalDate endDate);
}
