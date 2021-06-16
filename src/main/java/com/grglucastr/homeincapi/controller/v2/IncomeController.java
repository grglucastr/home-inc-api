package com.grglucastr.homeincapi.controller.v2;

import com.grglucastr.api.IncomesApi;
import com.grglucastr.homeincapi.model.Income;
import com.grglucastr.homeincapi.service.v2.IncomeService;
import com.grglucastr.model.IncomeRequest;
import com.grglucastr.model.IncomeResponse;
import com.grglucastr.model.IncomeSumResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class IncomeController implements IncomesApi {

    private IncomeService incomeService;
    private ModelMapper mapper;

    @Autowired
    public IncomeController(IncomeService incomeService, ModelMapper mapper) {
        this.incomeService = incomeService;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<List<IncomeResponse>> getIncomes() {
        final List<Income> incomes = incomeService.findAll();

        final List<IncomeResponse> incomesResponse = incomes
                .stream()
                .map(income -> mapper.map(income, IncomeResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(incomesResponse);
    }

    @Override
    public ResponseEntity<IncomeResponse> postNewIncome(IncomeRequest incomeRequest) {
        final Income income = mapper.map(incomeRequest, Income.class);
        final IncomeResponse response = mapper.map(incomeService.save(income), IncomeResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<IncomeSumResponse> getSumOfIncomes() {
        final List<Income> incomes = incomeService.findAll();
        final Optional<BigDecimal> sum = incomes.stream().map(Income::getAmount).reduce(BigDecimal::add);

        final IncomeSumResponse incomeSumResponse = new IncomeSumResponse();
        incomeSumResponse.setTotal(sum.orElse(BigDecimal.ZERO));
        return ResponseEntity.ok(incomeSumResponse);
    }
}