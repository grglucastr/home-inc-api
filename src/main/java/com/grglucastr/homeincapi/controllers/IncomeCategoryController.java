package com.grglucastr.homeincapi.controllers;

import com.grglucastr.homeincapi.api.IncomeCategoriesApi;
import com.grglucastr.homeincapi.models.IncomeCategory;
import com.grglucastr.homeincapi.models.IncomeCategoryRequest;
import com.grglucastr.homeincapi.models.IncomeCategoryResponse;
import com.grglucastr.homeincapi.models.User;
import com.grglucastr.homeincapi.services.IncomeCategoryService;
import com.grglucastr.homeincapi.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class IncomeCategoryController implements IncomeCategoriesApi {

    @Autowired
    private IncomeCategoryService incomeCategoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<List<IncomeCategoryResponse>> getIncomeCategories(Long userId) {

        final Optional<User> opUser = userService.findById(userId);
        if(opUser.isEmpty())
            return ResponseEntity.notFound().build();

        final List<IncomeCategory> incCategories = incomeCategoryService.listActiveIncomeCategories(userId);
        final List<IncomeCategoryResponse> incomeCategories = incCategories
                .stream()
                .map(i -> modelMapper.map(i, IncomeCategoryResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(incomeCategories);
    }

    @Override
    public ResponseEntity<IncomeCategoryResponse> postIncomeCategory(Long userId, IncomeCategoryRequest incomeCategoryRequest) {

        final Optional<User> opUser = userService.findById(userId);
        if(opUser.isEmpty())
            return ResponseEntity.notFound().build();

        final IncomeCategory incomeCategory = modelMapper.map(incomeCategoryRequest, IncomeCategory.class);
        final IncomeCategory incomeCategorySaved = incomeCategoryService.save(incomeCategory);
        final IncomeCategoryResponse response = modelMapper.map(incomeCategorySaved, IncomeCategoryResponse.class);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
