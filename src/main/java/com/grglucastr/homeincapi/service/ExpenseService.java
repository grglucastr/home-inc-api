package com.grglucastr.homeincapi.service;

import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> findAll(){
        return expenseRepository.findAll();
    }

    public ResponseEntity<Expense> findById(Long id){
        return expenseRepository.findById(id)
                .map(expense -> ResponseEntity.ok(expense))
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Expense> create(Expense expense){
        Expense expenseCreated = expenseRepository.save(expense);
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseCreated);
    }

    public ResponseEntity<Expense> delete(Long id){

        Optional<Expense> exp = expenseRepository.findById(id);
        if(exp.isEmpty()){
            throw new EmptyResultDataAccessException(1);
        }

        Expense expense = exp.get();
        expense.setIsActive(false);
        expenseRepository.save(expense);

        return ResponseEntity.noContent().build();
    }









}
