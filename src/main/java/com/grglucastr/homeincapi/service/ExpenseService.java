package com.grglucastr.homeincapi.service;

import com.grglucastr.homeincapi.dto.ExpenseDTO;
import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.repository.ExpenseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private ExpenseRepository expenseRepository;
    private ModelMapper mapper;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, ModelMapper mapper) {
        this.expenseRepository = expenseRepository;
        this.mapper = mapper;
    }

    public List<Expense> findAll(){
        return expenseRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparingLong(exp -> exp.getId()))
                .collect(Collectors.toList());
    }

    public Expense findById(Long id){
        Optional<Expense> exp =  expenseRepository.findById(id);
        if(exp.isPresent() && exp.get().getIsActive()){
            return exp.get();
        }
        throw new EmptyResultDataAccessException(0);
    }

    public Expense create(ExpenseDTO dto){
        Expense expObj = mapper.map(dto, Expense.class);
        expObj.setIsActive(true);
        return expenseRepository.save(expObj);
    }

    public void delete(Long id){
        Expense expense = findById(id);
        expense.setIsActive(false);
        expenseRepository.save(expense);
    }

    public Expense update(Long id, ExpenseDTO expenseDTO){
        Expense found = findById(id);
        Expense exp = mapper.map(expenseDTO, Expense.class);
        if(!found.isPaid() && exp.isPaid()){
            exp.setPaidDate(LocalDate.now());
        }
        return expenseRepository.save(exp);
    }









}
