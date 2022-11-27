package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.models.IncomeCategory;
import com.grglucastr.homeincapi.models.SpendingCategory;

import java.util.List;
import java.util.Optional;

public interface IncomeCategoryService {
    List<IncomeCategory> listActiveIncomeCategories(Long userId);
    IncomeCategory save(IncomeCategory incomeCategory);
    Optional<IncomeCategory> findById(Long incomeCategoryId);
}
