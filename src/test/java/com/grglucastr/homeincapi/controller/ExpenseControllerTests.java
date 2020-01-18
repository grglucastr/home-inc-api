package com.grglucastr.homeincapi.controller;


import com.grglucastr.homeincapi.dto.ExpenseDTO;
import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.service.ExpenseService;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseControllerTests {

    private ExpenseService expenseService;
    private Page<Expense> expensePage;
    private ModelMapper mapper;

    @Before
    public void init(){
        expenseService = mock(ExpenseService.class);
        expensePage = mock(Page.class);
        mapper = new ModelMapper();
    }

    @Test
    public void findAllShouldReturnAPageOfExpense(){

        List<Expense> expenses = Arrays.asList(new Expense(), new Expense(), new Expense());
        Page<Expense> pageResponse = new PageImpl<>(expenses);

        boolean active = true;
        boolean paid = false;
        int page = 0;
        int size = 10;

        when(expenseService.findAll(active, paid, page, size)).thenReturn(pageResponse);
        expensePage = expenseService.findAll(active, paid, page, size);

        assertEquals(3L, expensePage.getTotalElements());
    }

    @Test
    public void findByIdShouldReturnOneExpenseObject(){
        Expense exp = new Expense(236956L, "TEST");
        Long id = 236956L;
        when(expenseService.findById(id)).thenReturn(exp);

        Expense obj = expenseService.findById(id);
        assertEquals(obj.getId(), id);
    }

    @Test
    public void findByIdShouldReturnNullWhenNotFound() {
        Long id = 54564545L;
        when(expenseService.findById(id)).thenReturn(null);
        Expense obj = expenseService.findById(id);
        assertNull(obj);
    }

    @Test
    public void createShouldAddNewExpenseWhenReceiveExpenseDTO(){
        ExpenseDTO dto = new ExpenseDTO(
                "SomeTitle",
                "SomeDescription",
                new BigDecimal(720.00),
                LocalDate.now());

        Expense exp = mapper.map(dto, Expense.class);
        exp.setId(123L);

        when(expenseService.create(dto)).thenReturn(exp);

        Expense obj = expenseService.create(dto);
        assertNotNull(obj.getId());
        assertEquals(new BigDecimal("720"), obj.getCost());
    }

    @Test
    public void deleteShouldReturnNoContent(){
        Long id = 123L;
        expenseService.delete(id);
        verify(expenseService).delete(id);
    }

    @Test
    public void updateShouldChangeStatusOfExpenseToPaid(){

        ExpenseDTO dto = new ExpenseDTO();
        dto.setPaid(true);

        Expense exp = new Expense();
        exp.setPaid(true);

        Long id = 123L;
        when(expenseService.update(id, dto)).thenReturn(exp);


        Expense obj = expenseService.update(id, dto);

        assertTrue(obj.isPaid());


    }

}
