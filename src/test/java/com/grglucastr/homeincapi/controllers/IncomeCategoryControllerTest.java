package com.grglucastr.homeincapi.controllers;

import com.grglucastr.homeincapi.configurations.ModelMapperConfiguration;
import com.grglucastr.homeincapi.mocks.IncomeCategoryMocks;
import com.grglucastr.homeincapi.mocks.UserMocks;
import com.grglucastr.homeincapi.models.IncomeCategory;
import com.grglucastr.homeincapi.models.User;
import com.grglucastr.homeincapi.services.IncomeCategoryService;
import com.grglucastr.homeincapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {ModelMapperConfiguration.class, IncomeCategoryController.class})
public class IncomeCategoryControllerTest {

    private static final String BASE_URL = "/v3/users/{userId}/income-categories";
    private static final Long USER_ID = 12345L;

    @MockBean
    private IncomeCategoryService service;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        final User singleUser = UserMocks.getSingleUser();
        when(userService.findById(USER_ID)).thenReturn(Optional.of(singleUser));
    }

    @Test
    public void testListAllActiveIncomeCategories() throws Exception{

        final List<IncomeCategory> listActiveIncomeCategories =
                IncomeCategoryMocks.createListActiveIncomeCategories();

        when(service.listActiveIncomeCategories(USER_ID)).thenReturn(listActiveIncomeCategories);

        mockMvc.perform(get(BASE_URL, USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].active", is(true)))
                .andExpect(jsonPath("$.[0].insertDateTime", notNullValue()))
                .andExpect(jsonPath("$.[0].updateDateTime", nullValue()))
                .andExpect(jsonPath("$.[0].name", equalTo("Salary")))

                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[1].active", is(true)))
                .andExpect(jsonPath("$.[1].insertDateTime", notNullValue()))
                .andExpect(jsonPath("$.[1].updateDateTime", nullValue()))
                .andExpect(jsonPath("$.[1].name", equalTo("Dividends")))

                .andExpect(jsonPath("$.[2].id", is(3)))
                .andExpect(jsonPath("$.[2].active", is(true)))
                .andExpect(jsonPath("$.[2].insertDateTime", notNullValue()))
                .andExpect(jsonPath("$.[2].updateDateTime", notNullValue()))
                .andExpect(jsonPath("$.[2].updateDateTime", equalTo("2035-09-15T21:42:34")))
                .andExpect(jsonPath("$.[2].name", equalTo("Overtime")));
    }

    @Test
    public void testPostNewIncomeCategory() throws Exception {

        final IncomeCategory incomeCategory = IncomeCategoryMocks.createSingleIncomeCategory();
        ArgumentCaptor<IncomeCategory> captorRequest = ArgumentCaptor.forClass(IncomeCategory.class);

        when(service.save(captorRequest.capture())).thenReturn(incomeCategory);

        final MockHttpServletRequestBuilder post = post(BASE_URL, USER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Salary33\"}");

        mockMvc.perform(post)
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.active", is(true)))
                .andExpect(jsonPath("$.insertDateTime", equalTo( "2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.updateDateTime", nullValue()))
                .andExpect(jsonPath("$.name", equalTo("Salary")));

        final IncomeCategory value = captorRequest.getValue();
        assertThat(value, notNullValue());
        assertThat(value.getName(), equalTo("Salary33"));
    }
}