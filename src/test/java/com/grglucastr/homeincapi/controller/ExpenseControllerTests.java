package com.grglucastr.homeincapi.controller;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.mock;

import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.service.ExpenseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(controllers = ExpenseController.class)
public class ExpenseControllerTests {

    private MockMvc mockMvc;
    private ExpenseService expenseService;
    private ModelMapper modelMapper;

    @Before
    public void init(){

        expenseService = mock(ExpenseService.class);
        modelMapper = new ModelMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(new ExpenseController(expenseService, modelMapper)).build();
    }


    @Test
    public void test() throws Exception {
        // Given
        List<Expense> expenses = Arrays.asList(new Expense(1L, "Internet"), new Expense(2L, "Credit Card"));

        // When
        boolean active = true, paid = false;
        int page = 0, size = 10;
        when(expenseService.findAll(active, paid, page, size)).thenReturn(new PageImpl<>(expenses));

        // Then
        mockMvc.perform(get("/expenses?active=true&paid=false"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable").exists())
                .andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[0].title", is("Internet")))
                .andExpect(jsonPath("$.content[1].id", is(2)))
                .andExpect(jsonPath("$.content[1].title", is("Credit Card")))
                .andReturn();

    }
}
