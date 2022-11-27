package com.grglucastr.homeincapi.services.impl;

import com.grglucastr.homeincapi.models.SpendingCategory;
import com.grglucastr.homeincapi.repositories.SpendingCategoryRepository;
import com.grglucastr.homeincapi.services.SpendingCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpendingCategoryServiceImpl implements SpendingCategoryService {

    @Autowired
    private SpendingCategoryRepository repository;

    @Override
    public List<SpendingCategory> listActiveSpendingCategories(Long userId) {
        return repository.findAllByUserIdAndActiveTrue(userId);
    }

    @Override
    public SpendingCategory save(SpendingCategory spendingCategory) {
        return repository.save(spendingCategory);
    }

    @Override
    public Optional<SpendingCategory> findById(Long spendingCategoryId) {
        return repository.findById(spendingCategoryId);
    }
}
