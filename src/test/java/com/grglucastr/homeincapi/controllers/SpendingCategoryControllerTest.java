package com.grglucastr.homeincapi.controllers;

import com.grglucastr.homeincapi.configurations.ModelMapperConfiguration;
import com.grglucastr.homeincapi.services.SpendingCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {SpendingCategoryController.class, ModelMapperConfiguration.class})
public class SpendingCategoryControllerTest {

    private static final String SPENDING_CATEGORIES_URI = "/v3/users/{userId}/spending-categories";
    private static final long USER_ID = 1213L;

    @MockBean
    private SpendingCategoryService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getSpendingCategories() throws Exception {

        mockMvc.perform(
                get(SPENDING_CATEGORIES_URI, USER_ID))
                .andExpect(status().isOk());

    }
}