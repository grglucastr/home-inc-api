package com.grglucastr.homeincapi.controller.v2;

import com.grglucastr.homeincapi.enums.Periodicity;
import com.grglucastr.homeincapi.model.Income;
import com.grglucastr.homeincapi.service.v2.IncomeService;
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

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(controllers = IncomeController.class)
public class IncomeControllerTests {

    private static final String NEW_INCOME_PAYLOAD_JSON = "new-income-payload.json";
    private static final String UPDATE_INCOME_PAYLOAD_JSON = "update-income-payload.json";

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
        incomes.get(0).setType(Periodicity.WEEKLY);

        when(incomeService.findAll()).thenReturn(incomes);
        
        mockMvc.perform(get("/v2/incomes")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].amount", is(1500.00)))
                .andExpect(jsonPath("$.[0].description", is("Salário mensal")))
                .andExpect(jsonPath("$.[0].active", is(true)))
                .andExpect(jsonPath("$.[0].type", is("WEEKLY")))
                .andExpect(jsonPath("$.[0].insertDate").isNotEmpty())
                .andExpect(jsonPath("$.[0].updateDate").isNotEmpty())
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[1].amount", is(2500.00)))
                .andExpect(jsonPath("$.[1].description", is("Consultoria")))
                .andExpect(jsonPath("$.[1].active", is(true)))
                .andExpect(jsonPath("$.[1].type", is("MONTHLY")))
                .andExpect(jsonPath("$.[1].insertDate").isNotEmpty())
                .andExpect(jsonPath("$.[1].updateDate").isNotEmpty());
    }
    
    @Test
    public void testPostIncomes() throws Exception {

        final Income income = buildIncomeObject(1L, "123.45", "Tralala");
        income.setActive(false);

        final InputStream is = IncomeControllerTests.class.getResourceAsStream("/" + NEW_INCOME_PAYLOAD_JSON);
        assert is != null : NEW_INCOME_PAYLOAD_JSON + " not found ";

        when(incomeService.save(any())).thenReturn(income);

        final MockHttpServletRequestBuilder post = post("/v2/incomes")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(is.readAllBytes());
        
        mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.amount", is(123.45)))
                .andExpect(jsonPath("$.description", is("Tralala")))
                .andExpect(jsonPath("$.active", is(false)))
                .andExpect(jsonPath("$.type", is("MONTHLY")))
                .andExpect(jsonPath("$.accountingPeriodStart").exists())
                .andExpect(jsonPath("$.accountingPeriodStart").isNotEmpty())
                .andExpect(jsonPath("$.insertDate").isNotEmpty())
                .andExpect(jsonPath("$.updateDate").isNotEmpty());
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

    @Test
    public void testGetIncomeById() throws Exception {
        final Income income = buildIncomeObject(1L, "123.45", "Salary");

        when(incomeService.findById(1L)).thenReturn(Optional.of(income));

        final MockHttpServletRequestBuilder get = get("/v2/incomes/{incomeId}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(get)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.amount", is(123.45)))
                .andExpect(jsonPath("$.description", is("Salary")))
                .andExpect(jsonPath("$.active", is(true)))
                .andExpect(jsonPath("$.type", is("MONTHLY")))
                .andExpect(jsonPath("$.insertDate").isNotEmpty())
                .andExpect(jsonPath("$.updateDate").isNotEmpty());
    }

    @Test
    public void testGetIncomeByIdNotFound()  throws Exception {

        when(incomeService.findById(1L)).thenReturn(Optional.empty());

        final MockHttpServletRequestBuilder get = get("/v2/incomes/{incomeId}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(get)
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    public void testUpdateIncomeByIdNotFound()  throws Exception {

        final InputStream json = IncomeControllerTests.class
                .getResourceAsStream("/" + UPDATE_INCOME_PAYLOAD_JSON);

        assert json != null : UPDATE_INCOME_PAYLOAD_JSON + " not found.";

        when(incomeService.findById(1L)).thenReturn(Optional.empty());

        final MockHttpServletRequestBuilder put = put("/v2/incomes/{incomeId}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.readAllBytes());

        mockMvc.perform(put)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateIncomeById() throws Exception {

        final Income income = buildIncomeObject(1L, "1234.45", "Salary");
        income.setActive(false);

        when(incomeService.findById(1L)).thenReturn(Optional.of(income));

        income.setDescription("Independent service");
        income.setAmount(new BigDecimal("2500.50"));
        income.setType(Periodicity.WEEKLY);
        when(incomeService.save(any())).thenReturn(income);

        final InputStream json = IncomeControllerTests.class.getResourceAsStream("/" + UPDATE_INCOME_PAYLOAD_JSON);
        assert json != null : UPDATE_INCOME_PAYLOAD_JSON + " not found. ";

        final MockHttpServletRequestBuilder put = put("/v2/incomes/{incomeId}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.readAllBytes());

        mockMvc.perform(put)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.amount", is(2500.50)))
                .andExpect(jsonPath("$.description", is("Independent service")))
                .andExpect(jsonPath("$.active", is(false)))
                .andExpect(jsonPath("$.accountingPeriodStart").isNotEmpty())
                .andExpect(jsonPath("$.type", is("WEEKLY")))
                .andExpect(jsonPath("$.insertDate").isNotEmpty())
                .andExpect(jsonPath("$.updateDate").isNotEmpty());
    }

    @Test
    public void testDeleteIncomeById() throws Exception {

        final Income income = buildIncomeObject(1L, "123.89", "Payment");
        when(incomeService.findById(anyLong())).thenReturn(Optional.of(income));

        income.setActive(false);
        when(incomeService.save(any())).thenReturn(income);

        final MockHttpServletRequestBuilder delete = delete("/v2/incomes/{incomeId}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(delete).andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteIncomeByIdNotFound() throws Exception {
        when(incomeService.findById(anyLong())).thenReturn(Optional.empty());
        final MockHttpServletRequestBuilder delete = delete("/v2/incomes/{incomeId}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(delete).andExpect(status().isNotFound());
    }

    private Income buildIncomeObject(long id, String amount, String description) {
        final Income income = new Income();
        income.setId(id);
        income.setAmount(new BigDecimal(amount));
        income.setDescription(description);
        income.setActive(true);
        income.setType(Periodicity.MONTHLY);
        income.setAccountingPeriodStart(LocalDate.now());
        income.setInsertDate(LocalDate.now());
        income.setUpdateDate(LocalDate.now());
        return income;
    }

    private List<Income> buildListOfIncomes(){
        final Income income = buildIncomeObject(1L, "1500.00", "Salário mensal");
        final Income income2 = buildIncomeObject(2L, "2500.00", "Consultoria");
        return Arrays.asList(income, income2);
    }


}
