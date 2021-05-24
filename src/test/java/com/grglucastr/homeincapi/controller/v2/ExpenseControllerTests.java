package com.grglucastr.homeincapi.controller.v2;

import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.model.PaymentMethod;
import com.grglucastr.homeincapi.model.Periodicity;
import com.grglucastr.homeincapi.service.v2.ExpenseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
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
@WebMvcTest(controllers = ExpenseController.class)
public class ExpenseControllerTests {

    public static final String NEW_EXPENSE_PAYLOAD_JSON = "new-expense-payload.json";
    private ExpenseService expenseService;
    private ModelMapper modelMapper;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        expenseService = mock(ExpenseService.class);
        modelMapper = new ModelMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(new ExpenseController(expenseService, modelMapper)).build();
    }

    @Test
    public void getExpensesTest() throws Exception {

        final Expense expense1 = createSingleExpenseObject();
        final Expense expense2 = createSingleExpenseObject();
        final Expense expense3 = createSingleExpenseObject();
        expense2.setId(2L);
        expense3.setId(3L);

        List<Expense> expenses = Arrays.asList(expense1, expense2, expense3);
        when(expenseService.findAll()).thenReturn(expenses);

        mockMvc.perform(get("/v2/expenses"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].isActive", is(true)))
                .andExpect(jsonPath("$.[0].paid", is(false)));
    }

    @Test
    public void postExpensesTest() throws Exception {

        final Expense expense = createSingleExpenseObject();
        expense.setId(333L);

        when(expenseService.save(any(Expense.class))).thenReturn(expense);

        InputStream is = ExpenseControllerTests.class.getResourceAsStream("/" + NEW_EXPENSE_PAYLOAD_JSON);
        assert is != null : NEW_EXPENSE_PAYLOAD_JSON + " not found.";

        mockMvc.perform(post("/v2/expenses")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(is.readAllBytes()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(333)))
                .andExpect(jsonPath("$.cost", is(33.23)));
    }


    private Expense createSingleExpenseObject() {
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setPaidDate(LocalDate.of(2020,04,30));
        expense.setTitle("COPEL - April 2020");
        expense.setDescription("Electricity billl reference to the month of April");
        expense.setCost(new BigDecimal("33.23"));
        expense.setDueDate(LocalDate.of(2020,04,30));
        expense.setInvoiceDate(LocalDate.of(2020,04,25));
        expense.setServicePeriodStart(LocalDate.of(2020,03,25));
        expense.setServicePeriodEnd(LocalDate.of(2020,04,25));
        expense.setPeriodicity(Periodicity.MONTHLY);
        expense.setPaymentMethod(PaymentMethod.BANK_TRANSFER);
        return expense;
    }

}
