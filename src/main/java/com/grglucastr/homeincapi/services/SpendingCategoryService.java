package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.models.SpendingCategory;

import java.util.List;
import java.util.Optional;

public interface SpendingCategoryService {
    List<SpendingCategory> listActiveSpendingCategories(Long userId);
    SpendingCategory save(SpendingCategory spendingCategory);
    Optional<SpendingCategory> findById(Long spendingCategoryId);
}
