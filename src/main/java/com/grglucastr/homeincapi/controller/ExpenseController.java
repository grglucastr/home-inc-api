package com.grglucastr.homeincapi.controller;

import com.grglucastr.homeincapi.dto.ExpenseDTO;
import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
@Slf4j
public class ExpenseController {

    private ExpenseService expenseService;
    private ModelMapper mapper;

    @Autowired
    public ExpenseController(ExpenseService expenseService, ModelMapper modelMapper) {
        this.expenseService = expenseService;
        this.mapper = modelMapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Expense> findAll(
            @RequestParam(name = "active", required=false) boolean active,
            @RequestParam(name = "paid", required = false) boolean paid,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {

        return expenseService.findAll(active, paid, page, size);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> findById(@PathVariable  Long id){
        Expense exp = expenseService.findById(id);
        if (exp == null) {
            return ResponseEntity.notFound().build();
        }

        ExpenseDTO dto = mapper.map(exp, ExpenseDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ExpenseDTO> create(@RequestBody ExpenseDTO dto){
        Expense exp = expenseService.create(dto);
        ExpenseDTO expDTO = mapper.map(exp, ExpenseDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(expDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        expenseService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ExpenseDTO> update(@PathVariable  Long id, @RequestBody ExpenseDTO dto){
      Expense exp = expenseService.update(id, dto);
      ExpenseDTO expDTO = mapper.map(exp, ExpenseDTO.class);
      return ResponseEntity.ok(expDTO);
    }

}
