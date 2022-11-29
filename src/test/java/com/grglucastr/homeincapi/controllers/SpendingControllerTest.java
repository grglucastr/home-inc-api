package com.grglucastr.homeincapi.controllers;

import com.grglucastr.homeincapi.configurations.ModelMapperConfiguration;
import com.grglucastr.homeincapi.mocks.SpendingCategoryMocks;
import com.grglucastr.homeincapi.mocks.SpendingMocks;
import com.grglucastr.homeincapi.models.Spending;
import com.grglucastr.homeincapi.models.SpendingCategory;
import com.grglucastr.homeincapi.services.SpendingCategoryService;
import com.grglucastr.homeincapi.services.SpendingService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {ModelMapperConfiguration.class, SpendingController.class})
@ActiveProfiles("local")
class SpendingControllerTest {

    private static final String BASE_URL = "/v3/spending-categories/{spendingCategoryId}/spendings";
    private static final Long SPENDING_CATEGORY_ID = 123L;

    @MockBean
    private SpendingService service;

    @MockBean
    private SpendingCategoryService spendingCategoryService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        final SpendingCategory singleSpendingCategory = SpendingCategoryMocks.createSingleSpendingCategory();
        singleSpendingCategory.setSpendings(SpendingMocks.createListOfActiveSpendings());

        when(spendingCategoryService.findById(anyLong())).thenReturn(Optional.of(singleSpendingCategory));
    }

    @Test
    void testGetSpendings() throws Exception {

        mockMvc.perform(get(BASE_URL, SPENDING_CATEGORY_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].active", is(true)))
                .andExpect(jsonPath("$.[0].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[0].updateDateTime", nullValue()))
                .andExpect(jsonPath("$.[0].name", equalTo("Copel")))
                .andExpect(jsonPath("$.[0].installments", nullValue()))
                .andExpect(jsonPath("$.[0].description", equalTo("Electricity Bill")))
                .andExpect(jsonPath("$.[0].currencyCode", equalTo("BRL")))
                .andExpect(jsonPath("$.[0].periodicity", equalTo("MONTHLY")))
                .andExpect(jsonPath("$.[0].fixedIncomeFund", nullValue()))

                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[1].active", is(true)))
                .andExpect(jsonPath("$.[1].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[1].updateDateTime", nullValue()))
                .andExpect(jsonPath("$.[1].name", equalTo("Sanepar")))
                .andExpect(jsonPath("$.[1].installments", nullValue()))
                .andExpect(jsonPath("$.[1].description", equalTo("Electricity Bill")))
                .andExpect(jsonPath("$.[1].currencyCode", equalTo("BRL")))
                .andExpect(jsonPath("$.[1].periodicity", equalTo("MONTHLY")))
                .andExpect(jsonPath("$.[1].fixedIncomeFund", nullValue()))

                .andExpect(jsonPath("$.[2].id", is(3)))
                .andExpect(jsonPath("$.[2].active", is(true)))
                .andExpect(jsonPath("$.[2].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[2].updateDateTime", nullValue()))
                .andExpect(jsonPath("$.[2].name", equalTo("Oi")))
                .andExpect(jsonPath("$.[2].installments", nullValue()))
                .andExpect(jsonPath("$.[2].description", equalTo("Electricity Bill")))
                .andExpect(jsonPath("$.[2].currencyCode", equalTo("BRL")))
                .andExpect(jsonPath("$.[2].periodicity", equalTo("MONTHLY")))
                .andExpect(jsonPath("$.[2].fixedIncomeFund", nullValue()))

                .andExpect(jsonPath("$.[3].id", is(23123)))
                .andExpect(jsonPath("$.[3].active", is(true)))
                .andExpect(jsonPath("$.[3].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[3].updateDateTime", equalTo("2035-09-15T21:42:34")))
                .andExpect(jsonPath("$.[3].name", equalTo("Travel to Argentina")))
                .andExpect(jsonPath("$.[3].description", equalTo("Holidays in Argentina")))
                .andExpect(jsonPath("$.[3].currencyCode", equalTo("USD")))
                .andExpect(jsonPath("$.[3].installments", equalTo(12)))
                .andExpect(jsonPath("$.[3].periodicity", equalTo("MONTHLY")))
                .andExpect(jsonPath("$.[3].fixedIncomeFund", nullValue()))

                .andExpect(jsonPath("$.[4].id", is(93371)))
                .andExpect(jsonPath("$.[4].active", is(true)))
                .andExpect(jsonPath("$.[4].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[4].updateDateTime", equalTo("2035-09-15T21:42:34")))
                .andExpect(jsonPath("$.[4].name", equalTo("Tesouro Prefixado 2029")))
                .andExpect(jsonPath("$.[4].description", equalTo("Investimento em Renda Fixa, pre fixado")))
                .andExpect(jsonPath("$.[4].currencyCode", equalTo("BRL")))
                .andExpect(jsonPath("$.[4].periodicity", equalTo("MONTHLY")))
                .andExpect(jsonPath("$.[4].fixedIncomeFund", notNullValue()))
                .andExpect(jsonPath("$.[4].fixedIncomeFund.dueDate", equalTo("2029-01-01")))
                .andExpect(jsonPath("$.[4].fixedIncomeFund.annualProfitPercentage", equalTo(13.52)))
                .andExpect(jsonPath("$.[4].fixedIncomeFund.productPrice", equalTo(463.05)))
                .andExpect(jsonPath("$.[4].fixedIncomeFund.minAmountAllowed", equalTo(32.41)));
    }
}