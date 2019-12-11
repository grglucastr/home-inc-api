package com.grglucastr.homeincapi.controller;

import com.grglucastr.homeincapi.model.ExpenseType;
import com.grglucastr.homeincapi.service.ExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/expense-types")
public class ExpenseTypeController {

    private ExpenseTypeService service;

    @Autowired
    public ExpenseTypeController(ExpenseTypeService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ExpenseType> listAllTypes(){
        return service.listAll();
    }

}
