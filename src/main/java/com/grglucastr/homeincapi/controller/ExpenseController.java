package com.grglucastr.homeincapi.controller;

import com.grglucastr.homeincapi.dto.ExpenseDTO;
import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
@Slf4j
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Expense> create(@RequestBody ExpenseDTO dto){
        Expense exp = expenseService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(exp);
    }
}
