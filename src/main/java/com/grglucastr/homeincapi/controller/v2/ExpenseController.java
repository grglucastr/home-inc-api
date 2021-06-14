package com.grglucastr.homeincapi.controller.v2;

import com.grglucastr.api.ExpensesApi;
import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.service.v2.ExpenseService;
import com.grglucastr.model.ExpenseFilter;
import com.grglucastr.model.ExpenseRequest;
import com.grglucastr.model.ExpenseResponse;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController("ExpenseV2Controller")
@Slf4j
public class ExpenseController implements ExpensesApi {

    private ExpenseService expenseService;
    private ModelMapper mapper;

    @Autowired
    public ExpenseController(ExpenseService expenseService, ModelMapper modelMapper) {
        this.expenseService = expenseService;
        this.mapper = modelMapper;
    }

    @Override
    public ResponseEntity<List<ExpenseResponse>> getExpenses(ExpenseFilter filter) {
        final List<Expense> expenses = expenseService.findAll();
        final List<ExpenseResponse> expenseResponses = expenses.stream()
                .map(expense -> mapper.map(expense, ExpenseResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(expenseResponses);
    }

    @Override
    public ResponseEntity<ExpenseResponse> postExpenses(ExpenseRequest expenseRequest) {
        final Expense expense = mapper.map(expenseRequest, Expense.class);
        final Expense expenseResponse = expenseService.save(expense);
        final ExpenseResponse response = mapper.map(expenseResponse, ExpenseResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<ExpenseResponse> payExpense(Long expenseId) {

        final Optional<Expense> optExpense = expenseService.findById(expenseId);
        if(optExpense.isEmpty())
            return ResponseEntity.notFound().build();

        final Expense expense = optExpense.get();
        return payExpense(expense);
    }

    private ResponseEntity<ExpenseResponse> payExpense(Expense expense){
        expense.setPaid(true);
        expense.setPaidDate(LocalDate.now());
        expenseService.save(expense);

        final ExpenseResponse response = mapper.map(expense, ExpenseResponse.class);
        return ResponseEntity.ok(response);
    }
}
