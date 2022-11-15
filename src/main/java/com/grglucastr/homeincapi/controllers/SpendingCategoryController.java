package com.grglucastr.homeincapi.controllers;

import com.grglucastr.homeincapi.api.SpendingCategoriesApi;
import com.grglucastr.homeincapi.models.SpendingCategoryResponse;
import com.grglucastr.homeincapi.services.SpendingCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class SpendingCategoryController implements SpendingCategoriesApi {

    @Autowired
    private SpendingCategoryService service;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ResponseEntity<List<SpendingCategoryResponse>> getSpendingCategories(Long userId) {
        return ResponseEntity.ok(Collections.emptyList());
    }
}
