package com.grglucastr.homeincapi.controller.v2;

import com.grglucastr.homeincapi.model.Income;
import com.grglucastr.homeincapi.service.v2.IncomeService;
import com.grglucastr.homeincapi.util.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(controllers = IncomeController.class)
public class IncomeControllerTests {

    private IncomeService incomeService;
    private ModelMapper mapper;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        incomeService = mock(IncomeService.class);
        mapper = new ModelMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(new IncomeController(incomeService, mapper)).build();
    }

    @Test
    public void testGetIncomes() throws Exception {

        final List<Income> incomes = buildListOfIncomes();
        when(incomeService.findAll()).thenReturn(incomes);
        
        mockMvc.perform(get("/v2/incomes")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].amount", is(1500.00)))
                .andExpect(jsonPath("$.[0].description", is("Salário mensal")))
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[1].amount", is(2500.00)))
                .andExpect(jsonPath("$.[1].description", is("Consultoria")));
    }
    
    @Test
    public void testPostIncomes() throws Exception {

        final Income income = buildIncomeObject(1L, "123.45", "Tralala");

        when(incomeService.save(any())).thenReturn(income);

        final MockHttpServletRequestBuilder post = post("/v2/incomes")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(StringUtils.asJsonString(income));
        
        mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.amount", is(123.45)))
                .andExpect(jsonPath("$.description", is("Tralala")));
    }

    @Test
    public void testGetSumOfIncomes() throws Exception {

        final List<Income> incomes = buildListOfIncomes();

        when(incomeService.findAll()).thenReturn(incomes);

        final MockHttpServletRequestBuilder get = get("/v2/incomes/sum")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(get)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total", is(4000.00)));


    }

    private Income buildIncomeObject(long id, String amount, String description) {
        final Income income = new Income();
        income.setId(id);
        income.setAmount(new BigDecimal(amount));
        income.setDescription(description);
        return income;
    }

    private List<Income> buildListOfIncomes(){
        final Income income = buildIncomeObject(1L, "1500.00", "Salário mensal");
        final Income income2 = buildIncomeObject(2L, "2500.00", "Consultoria");
        return Arrays.asList(income, income2);
    }


}
