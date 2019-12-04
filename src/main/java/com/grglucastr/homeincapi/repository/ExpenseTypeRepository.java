package com.grglucastr.homeincapi.repository;

import com.grglucastr.homeincapi.model.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {
}
