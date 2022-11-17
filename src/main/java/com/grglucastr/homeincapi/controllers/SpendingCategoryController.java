package com.grglucastr.homeincapi.controllers;

import com.grglucastr.homeincapi.api.SpendingCategoriesApi;
import com.grglucastr.homeincapi.models.SpendingCategory;
import com.grglucastr.homeincapi.models.SpendingCategoryRequest;
import com.grglucastr.homeincapi.models.SpendingCategoryResponse;
import com.grglucastr.homeincapi.models.User;
import com.grglucastr.homeincapi.services.SpendingCategoryService;
import com.grglucastr.homeincapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class SpendingCategoryController implements SpendingCategoriesApi {

    @Autowired
    private SpendingCategoryService service;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ResponseEntity<List<SpendingCategoryResponse>> getSpendingCategories(Long userId) {

        final Optional<User> user = userService.findById(userId);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        final List<SpendingCategory> spendingCategories = service.listActiveSpendingCategories(userId);
        final List<SpendingCategoryResponse> response = spendingCategories.stream()
                .map(sc -> modelMapper.map(sc, SpendingCategoryResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SpendingCategoryResponse> postSpendingCategory(Long userId, SpendingCategoryRequest spendingCategoryRequest) {

        final Optional<User> user = userService.findById(userId);
        if(user.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        final SpendingCategory newSpendingCategory = new SpendingCategory(spendingCategoryRequest.getName());
        final SpendingCategory spendingCategory = service.save(newSpendingCategory, userId);
        final SpendingCategoryResponse response = modelMapper.map(spendingCategory, SpendingCategoryResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
