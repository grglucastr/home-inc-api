package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.models.IncomeCategory;

import java.util.List;

public interface IncomeCategoryService {

    List<IncomeCategory> findAllActiveIncomeCategories();

    IncomeCategory save(IncomeCategory incomeCategory);
}
