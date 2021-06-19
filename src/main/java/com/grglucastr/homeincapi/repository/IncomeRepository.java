package com.grglucastr.homeincapi.repository;

import com.grglucastr.homeincapi.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Override
    @Query("SELECT i FROM income i ORDER BY i.id ASC")
    List<Income> findAll();
}
