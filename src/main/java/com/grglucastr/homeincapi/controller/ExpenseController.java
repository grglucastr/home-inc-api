package com.grglucastr.homeincapi.controller;

import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Expense> findAll(){
        return expenseService.findAll();
    }
}
