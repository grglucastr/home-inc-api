package com.grglucastr.homeincapi.controllers;

import com.grglucastr.homeincapi.api.SpendingsApi;
import com.grglucastr.homeincapi.models.Spending;
import com.grglucastr.homeincapi.models.SpendingCategory;
import com.grglucastr.homeincapi.models.SpendingResponse;
import com.grglucastr.homeincapi.services.SpendingCategoryService;
import com.grglucastr.homeincapi.services.SpendingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class SpendingController implements SpendingsApi {

    @Autowired
    private SpendingService spendingService;

    @Autowired
    private SpendingCategoryService spendingCategoryService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<List<SpendingResponse>> getSpendings(Long spendingCategoryId) {

        final Optional<SpendingCategory> opSpendingCategory = spendingCategoryService.findById(spendingCategoryId);
        if(opSpendingCategory.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        final List<Spending> spendings = opSpendingCategory.get().getSpendings();
        final List<SpendingResponse> response = spendings
                .stream()
                .map(s -> modelMapper.map(s, SpendingResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
