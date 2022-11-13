package com.grglucastr.homeincapi.services.impl;

import com.grglucastr.homeincapi.models.SpendingCategory;
import com.grglucastr.homeincapi.repositories.SpendingCategoryRepository;
import com.grglucastr.homeincapi.services.SpendingCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpendingCategoryServiceImpl implements SpendingCategoryService {

    @Autowired
    private SpendingCategoryRepository repository;

    @Override
    public List<SpendingCategory> listActiveSpendingCategories(Long userId) {
        return repository.findAllWhereActiveIsTrue();
    }
}
