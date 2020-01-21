package com.grglucastr.homeincapi.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.google.gson.JsonObject;
import com.grglucastr.homeincapi.dto.ExpenseDTO;
import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(controllers = ExpenseController.class)
@Slf4j
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
    public void shouldDisableExpense() throws Exception{

        //Given
        long id = 123L;

        //When
        expenseService.delete(id);

        //Then
        mockMvc.perform(delete("/expenses/123"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldListActiveAndUnpaidExpenses() throws Exception {
        // Given
        List<Expense> expenses = Arrays.asList(new Expense(1L, "Internet"), new Expense(2L, "Credit Card"));

        // When
        boolean active = true, paid = false;
        int page = 0, size = 10;
        when(expenseService.findAll(active, paid, page, size)).thenReturn(new PageImpl<>(expenses));

        // Then
        String urlTemplate = "/expenses?active=true&paid=false";
        getRequestResultActions(urlTemplate)
                .andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.content[0].isActive", is(true)))
                .andExpect(jsonPath("$.content[0].paid", is(false)))
                .andExpect(jsonPath("$.content[1].isActive", is(true)))
                .andExpect(jsonPath("$.content[1].paid", is(false)));
    }

    @Test
    public void shoudlListAllActiveAndPaidExpenses() throws  Exception{
        //Given
        Expense exp1 = new Expense(1L, "Exp1");
        exp1.setPaid(true);

        Expense exp2 = new Expense(2L, "Exp1");
        exp2.setPaid(true);

        List<Expense> expenses = Arrays.asList(exp1, exp2);

        //When
        boolean active = true, paid = true;
        int page = 0, size = 10;
        when(expenseService.findAll(active,paid,page,size)).thenReturn(new PageImpl<>(expenses));

        //Then
        String urlTemplate = "/expenses?active=true&paid=true";
        getRequestResultActions(urlTemplate)
                .andExpect(jsonPath("$.totalElements", is(2)))
                .andExpect(jsonPath("$.content[0].isActive", is(true)))
                .andExpect(jsonPath("$.content[0].paid", is(true)))
                .andExpect(jsonPath("$.content[1].isActive", is(true)))
                .andExpect(jsonPath("$.content[1].paid", is(true)));
    }

    @Test
    public void shouldReturnASingleExpense() throws Exception{
        //Given
        Expense expense = new Expense(1L, "asdfasdfasfdasd");
        long id = 1L;

        //When
        when(expenseService.findById(id)).thenReturn(expense);

        //Then
        String url = "/expenses/1";
        mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }


    @Test
    public void shouldUpdateAttributesOfSingleExpense() throws Exception {
        //Given
        JsonObject expJson = new JsonObject();
        expJson.addProperty("id","100");
        expJson.addProperty("isActive","true");
        expJson.addProperty("title","Viagem ao Chile");
        expJson.addProperty("cost","452.36");
        expJson.addProperty("dueDate", "2020-04-30");

        Expense expense = new Expense(100L, "Viagem ao Chile", "", new BigDecimal("4520.36"),
                LocalDate.of(2020,06,15));

        //When
        when(expenseService.update(any(Long.class), any(ExpenseDTO.class))).thenReturn(expense);

        //Then
        mockMvc.perform(put("/expenses/100")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .param("id","100")
                .content(expJson.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cost", is(4520.36)))
                .andExpect(jsonPath("$.dueDate[0]", is(2020)))
                .andExpect(jsonPath("$.dueDate[1]", is(06)))
                .andExpect(jsonPath("$.dueDate[2]", is(15)));
    }

    @Test
    public void shouldThrowsNotFoundExceptionWhenTryToUpdateExpense() throws Exception {


        mockMvc.perform(put("expenses/10000")
                .param("id", "10000")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(""))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    public void shouldCreateANewExpense() throws Exception{

        //Given
        JsonObject expJson = new JsonObject();
        expJson.addProperty("title","Credit Card Bill");
        expJson.addProperty("cost", "1950.65");
        expJson.addProperty("dueDate", "2020-02-20");
        expJson.addProperty("description", "Lorem ipsum dolor sit amet");

        //When
        when(expenseService
                .create(new ExpenseDTO(
                                "Credit Card Bill",
                                "Lorem ipsum dolor sit amet",
                                new BigDecimal("1950.65"),
                                LocalDate.of(2020,02,20))))
                .thenReturn(new Expense(
                        123456789L,
                        "Credit Card Bill",
                        "Lorem ipsum dolor sit amet",
                        new BigDecimal("1950.65"),
                        LocalDate.of(2020,02,20)));


        //Then
        mockMvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(expJson.toString()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(123456789)))
                .andExpect(jsonPath("$.isActive", is(true)))
                .andExpect(jsonPath("$.paid", is(false)))
                .andExpect(jsonPath("$.title", is("Credit Card Bill")));
    }


    private ResultActions getRequestResultActions(String urlTemplate) throws Exception{
        return  mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable").exists());
    }
}