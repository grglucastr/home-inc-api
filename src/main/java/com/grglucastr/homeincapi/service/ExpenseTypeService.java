package com.grglucastr.homeincapi.service;


import com.grglucastr.homeincapi.model.ExpenseType;
import com.grglucastr.homeincapi.repository.ExpenseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseTypeService {

    private ExpenseTypeRepository repository;

    @Autowired
    public ExpenseTypeService(ExpenseTypeRepository repository) {
        this.repository = repository;
    }

    public List<ExpenseType> listAll(){
        return repository.findAll();
    }
}
