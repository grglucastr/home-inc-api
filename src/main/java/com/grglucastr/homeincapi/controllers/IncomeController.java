package com.grglucastr.homeincapi.controllers;

import com.grglucastr.homeincapi.api.IncomesApi;
import com.grglucastr.homeincapi.models.Income;
import com.grglucastr.homeincapi.models.IncomeCategory;
import com.grglucastr.homeincapi.models.IncomeRequest;
import com.grglucastr.homeincapi.models.IncomeResponse;
import com.grglucastr.homeincapi.services.IncomeCategoryService;
import com.grglucastr.homeincapi.services.IncomeService;
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
public class IncomeController implements IncomesApi {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private IncomeCategoryService incomeCategoryService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<List<IncomeResponse>> getIncomes(Long incomeCategoryId) {
        final Optional<IncomeCategory> opIncomeCategory = incomeCategoryService.findById(incomeCategoryId);
        if(opIncomeCategory.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        final List<Income> incomes = incomeService.listActiveIncomes(incomeCategoryId);

        final List<IncomeResponse> response = incomes
                .stream()
                .map(i -> modelMapper.map(i, IncomeResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<IncomeResponse> postIncome(Long incomeCategoryId, IncomeRequest incomeRequest) {
        final Optional<IncomeCategory> opIncomeCategory = incomeCategoryService.findById(incomeCategoryId);
        if(opIncomeCategory.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        final Income newIncome = modelMapper.map(incomeRequest, Income.class);
        newIncome.setIncomeCategory(opIncomeCategory.get());
        final Income savedIncome = incomeService.save(newIncome);
        final IncomeResponse response = modelMapper.map(savedIncome, IncomeResponse.class);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
