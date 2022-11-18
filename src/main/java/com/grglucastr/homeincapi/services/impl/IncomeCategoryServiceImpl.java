package com.grglucastr.homeincapi.services.impl;

import com.grglucastr.homeincapi.models.IncomeCategory;
import com.grglucastr.homeincapi.repositories.IncomeCategoryRepository;
import com.grglucastr.homeincapi.services.IncomeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeCategoryServiceImpl implements IncomeCategoryService {

    @Autowired
    private IncomeCategoryRepository repository;

    @Override
    public List<IncomeCategory> listActiveIncomeCategories() {
        return repository.findAllByActiveTrue();
    }

    @Override
    public IncomeCategory save(IncomeCategory incomeCategory) {
        return repository.save(incomeCategory);
    }
}
