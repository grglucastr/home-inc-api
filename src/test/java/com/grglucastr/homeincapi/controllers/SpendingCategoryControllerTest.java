package com.grglucastr.homeincapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.grglucastr.homeincapi.configurations.ModelMapperConfiguration;
import com.grglucastr.homeincapi.mocks.SpendingCategoryMocks;
import com.grglucastr.homeincapi.mocks.UserMocks;
import com.grglucastr.homeincapi.models.SpendingCategory;
import com.grglucastr.homeincapi.models.SpendingCategoryRequest;
import com.grglucastr.homeincapi.models.User;
import com.grglucastr.homeincapi.services.SpendingCategoryService;
import com.grglucastr.homeincapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {SpendingCategoryController.class, ModelMapperConfiguration.class})
public class SpendingCategoryControllerTest {

    private static final String SPENDING_CATEGORIES_URI = "/v3/users/{userId}/spending-categories";
    private static final long USER_ID = 1213L;

    @MockBean
    private SpendingCategoryService service;

    @MockBean
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        final User singleUser = UserMocks.getSingleUser();
        when(userService.findById(USER_ID)).thenReturn(Optional.of(singleUser));
    }

    @Test
    void getSpendingCategories() throws Exception {

        final List<SpendingCategory> spendingCategories = SpendingCategoryMocks
                .createListOfActiveSpendingCategories();

        when(service.listActiveSpendingCategories(USER_ID)).thenReturn(spendingCategories);

        mockMvc.perform(
                get(SPENDING_CATEGORIES_URI, USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].active", is(true)))
                .andExpect(jsonPath("$.[0].insertDateTime", notNullValue()))
                .andExpect(jsonPath("$.[0].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[0].updateDateTime", nullValue()))
                .andExpect(jsonPath("$.[0].name", is("Electricity")))
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[1].active", is(true)))
                .andExpect(jsonPath("$.[1].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[1].updateDateTime", equalTo("2035-09-15T21:42:34")))
                .andExpect(jsonPath("$.[1].name", is("Fuel")));
    }

    @Test
    void getSpendingCategoriesButUserNotFound() throws Exception {

        when(userService.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get(SPENDING_CATEGORIES_URI, USER_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void postSpendingCategory() throws Exception {

        final SpendingCategory spendingCategoryRequest = SpendingCategoryMocks.createSingleSpendingCategory();
        spendingCategoryRequest.setId(null);
        spendingCategoryRequest.setInsertDateTime(null);
        spendingCategoryRequest.setUpdateDateTime(null);

        final SpendingCategory spendingCategoryResponse = SpendingCategoryMocks.createSingleSpendingCategory();

        when(service.save(spendingCategoryRequest)).thenReturn(spendingCategoryResponse);
        when(userService.findById(USER_ID)).thenReturn(Optional.of(UserMocks.getSingleUser()));

        final SpendingCategoryRequest request = new SpendingCategoryRequest();
        request.setName("Electricity");

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        final String payloadRequest = objectWriter.writeValueAsString(request);
        mockMvc.perform(post(SPENDING_CATEGORIES_URI, USER_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payloadRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Electricity")))
                .andExpect(jsonPath("$.active", is(true)))
                .andExpect(jsonPath("$.insertDateTime", notNullValue()))
                .andExpect(jsonPath("$.insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.updateDateTime", nullValue()));
    }
}