package com.grglucastr.homeincapi.services.impl;

import com.grglucastr.homeincapi.models.IncomeCategory;
import com.grglucastr.homeincapi.repositories.IncomeCategoryRepository;
import com.grglucastr.homeincapi.services.IncomeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeCategoryServiceImpl implements IncomeCategoryService {

    @Autowired
    private IncomeCategoryRepository repository;

    @Override
    public List<IncomeCategory> listActiveIncomeCategories(Long userId) {
        return repository.findAllByUserIdAndActiveTrue(userId);
    }

    @Override
    public IncomeCategory save(IncomeCategory incomeCategory) {
        return repository.save(incomeCategory);
    }

    @Override
    public Optional<IncomeCategory> findById(Long incomeCategoryId) {
        return repository.findById(incomeCategoryId);
    }
}
