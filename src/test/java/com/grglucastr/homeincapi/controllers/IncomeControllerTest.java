package com.grglucastr.homeincapi.controllers;

import com.grglucastr.homeincapi.configurations.ModelMapperConfiguration;
import com.grglucastr.homeincapi.enums.Periodicity;
import com.grglucastr.homeincapi.mocks.IncomeCategoryMocks;
import com.grglucastr.homeincapi.mocks.IncomeMocks;
import com.grglucastr.homeincapi.models.Income;
import com.grglucastr.homeincapi.models.IncomeCategory;
import com.grglucastr.homeincapi.services.IncomeCategoryService;
import com.grglucastr.homeincapi.services.IncomeService;
import com.grglucastr.homeincapi.services.UserService;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {ModelMapperConfiguration.class, IncomeController.class})
@ActiveProfiles("local")
public class IncomeControllerTest {

    private static final Long INCOME_CATEGORY_ID = 12345L;
    public static final String BASE_URL = "/v3/income-categories/{incomeCategoryId}/incomes";
    public static final String INCOME_REQUEST_PAYLOAD = "src/test/resources/income-request-payload.json";
    public static final String CHARSET = "UTF-8";

    @MockBean
    private IncomeService service;

    @MockBean
    private IncomeCategoryService incomeCategoryService;

    @MockBean
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        final IncomeCategory incomeCategory = IncomeCategoryMocks.createSingleIncomeCategory();
        when(incomeCategoryService.findById(any())).thenReturn(Optional.of(incomeCategory));
    }

    @Test
    void testListAllActiveIncomes() throws Exception {

        final List<Income> listOfActiveIncomes = IncomeMocks.createListOfActiveIncomes();

        when(service.listActiveIncomes(INCOME_CATEGORY_ID)).thenReturn(listOfActiveIncomes);

        mockMvc.perform(get(BASE_URL, INCOME_CATEGORY_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].active", is(true)))
                .andExpect(jsonPath("$.[0].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[0].updateDateTime", nullValue()))
                .andExpect(jsonPath("$.[0].name", equalTo("Salary")))
                .andExpect(jsonPath("$.[0].amount", equalTo(2000)))
                .andExpect(jsonPath("$.[0].category", equalTo("Salary")))
                .andExpect(jsonPath("$.[0].currencyCode", equalTo("BRL")))
                .andExpect(jsonPath("$.[0].periodicity", equalTo("MONTHLY")))

                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[1].active", is(true)))
                .andExpect(jsonPath("$.[1].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[1].updateDateTime", nullValue()))
                .andExpect(jsonPath("$.[1].name", equalTo("Overtime")))
                .andExpect(jsonPath("$.[1].amount", equalTo(1000.44)))
                .andExpect(jsonPath("$.[1].category", equalTo("Salary")))
                .andExpect(jsonPath("$.[1].currencyCode", equalTo("BRL")))
                .andExpect(jsonPath("$.[1].periodicity", equalTo("MONTHLY")))

                .andExpect(jsonPath("$.[2].id", is(3)))
                .andExpect(jsonPath("$.[2].active", is(true)))
                .andExpect(jsonPath("$.[2].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[2].updateDateTime", equalTo("2035-09-15T21:42:34")))
                .andExpect(jsonPath("$.[2].name", equalTo("API Freelas")))
                .andExpect(jsonPath("$.[2].amount", equalTo(500.33)))
                .andExpect(jsonPath("$.[2].category", equalTo("Salary")))
                .andExpect(jsonPath("$.[2].currencyCode", equalTo("USD")))
                .andExpect(jsonPath("$.[2].periodicity", equalTo("LOOSE")));
    }

    @Test
    public void testPostNewIncome() throws Exception{
        final Income singleIncome = IncomeMocks.createSingleIncome();

        final ArgumentCaptor<Income> captorIncome = ArgumentCaptor.forClass(Income.class);
        when(service.save(captorIncome.capture())).thenReturn(singleIncome);

        final String content = FileUtils
                .readFileToString(new File(INCOME_REQUEST_PAYLOAD), CHARSET)
                .replace("\n","")
                .replace(" ","");

        final MockHttpServletRequestBuilder post = post(BASE_URL, INCOME_CATEGORY_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(post)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.active", is(true)))
                .andExpect(jsonPath("$.insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.updateDateTime", nullValue()))
                .andExpect(jsonPath("$.name", equalTo("Salary")))
                .andExpect(jsonPath("$.amount", equalTo(2000)))
                .andExpect(jsonPath("$.category", equalTo("Salary")))
                .andExpect(jsonPath("$.currencyCode", equalTo("BRL")))
                .andExpect(jsonPath("$.periodicity", equalTo("MONTHLY")));

        final Income incomePayloadConverted = captorIncome.getValue();
        assertThat(incomePayloadConverted, notNullValue());
        assertThat(incomePayloadConverted.getName(), equalTo("Salary"));
        assertThat(incomePayloadConverted.getAmount(), equalTo(new BigDecimal("2500.33")));
        assertThat(incomePayloadConverted.getCurrencyCode(), equalTo("BRL"));
        assertThat(incomePayloadConverted.getPeriodicity(), equalTo(Periodicity.MONTHLY));

        assertThat(incomePayloadConverted.getIncomeCategory(), notNullValue());
        assertThat(incomePayloadConverted.getIncomeCategory().getName(), equalTo("Salary"));
    }
}