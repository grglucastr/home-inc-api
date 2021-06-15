package com.grglucastr.homeincapi.controller.v2;

import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.model.PaymentMethod;
import com.grglucastr.homeincapi.model.Periodicity;
import com.grglucastr.homeincapi.service.v2.ExpenseService;
import org.junit.Assert;
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
import java.util.ArrayList;
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

    @Test
    public void testPayExpense() throws Exception {

        final Expense expense = createSingleExpenseObject();

        when(expenseService.findById(anyLong())).thenReturn(Optional.of(expense));

        Assert.assertFalse(expense.isPaid());
        Assert.assertNull(expense.getPaidDate());

        when(expenseService.save(any())).thenReturn(expense);

        mockMvc.perform(post("/v2/expenses/{expenseId}/pay", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paid", is(true)))
                .andExpect(jsonPath("$.paidDate[0]", is(LocalDate.now().getYear())))
                .andExpect(jsonPath("$.paidDate[1]", is(LocalDate.now().getMonthValue())))
                .andExpect(jsonPath("$.paidDate[2]", is(LocalDate.now().getDayOfMonth())));
    }

    @Test
    public void testPayExpenseButExpenseNotFound() throws Exception {

        when(expenseService.findById(2L)).thenReturn(Optional.empty());

        final MockHttpServletRequestBuilder post = post("/v2/expenses/{expenseId}/pay", 2L)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(post).andExpect(status().isNotFound());
    }

    @Test
    public void testGetExpenseById() throws Exception {
        final Expense expense = createSingleExpenseObject();
        expense.setPaidDate(LocalDate.now());
        expense.setPaid(true);

        when(expenseService.findById(1L)).thenReturn(Optional.of(expense));

        final MockHttpServletRequestBuilder get = get("/v2/expenses/{expenseId}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(get)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.isActive", is(true)))
                .andExpect(jsonPath("$.paidDate[0]", is(LocalDate.now().getYear())))
                .andExpect(jsonPath("$.paidDate[1]", is(LocalDate.now().getMonthValue())))
                .andExpect(jsonPath("$.paidDate[2]", is(LocalDate.now().getDayOfMonth())))
                .andExpect(jsonPath("$.title", is("COPEL - April 2020")))
                .andExpect(jsonPath("$.description", is("Electricity billl reference to the month of April")))
                .andExpect(jsonPath("$.cost", is(33.23)))
                .andExpect(jsonPath("$.dueDate[0]", is(2020)))
                .andExpect(jsonPath("$.dueDate[1]", is(4)))
                .andExpect(jsonPath("$.dueDate[2]", is(30)))
                .andExpect(jsonPath("$.paid", is(true)))
                .andExpect(jsonPath("$.invoiceDate[0]", is(2020)))
                .andExpect(jsonPath("$.invoiceDate[1]", is(4)))
                .andExpect(jsonPath("$.invoiceDate[2]", is(25)))
                .andExpect(jsonPath("$.servicePeriodStart[0]", is(2020)))
                .andExpect(jsonPath("$.servicePeriodStart[1]", is(3)))
                .andExpect(jsonPath("$.servicePeriodStart[2]", is(25)))
                .andExpect(jsonPath("$.servicePeriodEnd[0]", is(2020)))
                .andExpect(jsonPath("$.servicePeriodEnd[1]", is(4)))
                .andExpect(jsonPath("$.servicePeriodEnd[2]", is(25)))
                .andExpect(jsonPath("$.periodicity", is(Periodicity.MONTHLY.toString().toLowerCase())))
                .andExpect(jsonPath("$.paymentMethod", is(PaymentMethod.BANK_TRANSFER.toString().toLowerCase())));
    }

    @Test
    public void testGetExpenseByIdButNotFound() throws Exception {
        when(expenseService.findById(2L)).thenReturn(Optional.empty());

        final MockHttpServletRequestBuilder get = get("/v2/expenses/{expenseId}", 2L)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(get).andExpect(status().isNotFound());
    }

    @Test
    public void testInvalidateExpenseButNotFound() throws Exception {

        when(expenseService.findById(2L)).thenReturn(Optional.empty());

        final MockHttpServletRequestBuilder get = get("/v2/expenses/{expenseId}", 2L)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(get).andExpect(status().isNotFound());
    }

    @Test
    public void testInvalidateExpense() throws Exception {
        final Expense expense = createSingleExpenseObject();
        when(expenseService.findById(1L)).thenReturn(Optional.of(expense));

        Assert.assertTrue(expense.getIsActive());

        mockMvc.perform(delete("/v2/expenses/{expenseId}/invalidate", 1L))
                .andExpect(status().isNoContent());

    }

    @Test
    public void testMonthlySummary() throws Exception {
        final Expense exp1 = createSingleExpenseObject();
        exp1.setPaidDate(LocalDate.of(2020, 4, 30));
        exp1.setPaid(true);

        final Expense exp2 = createSingleExpenseObject();
        exp2.setId(2L);
        exp2.setCost(new BigDecimal("19.78"));
        exp1.setPaidDate(LocalDate.of(2020, 4, 30));
        exp2.setPaid(true);

        final Expense exp3 = createSingleExpenseObject();
        exp3.setId(3L);
        exp3.setCost(new BigDecimal("999.99"));

        final Expense exp4 = createSingleExpenseObject();
        exp4.setId(4L);
        exp4.setCost(new BigDecimal("1000.50"));

        when(expenseService.findByMonth(4)).thenReturn(Arrays.asList(exp1, exp2, exp3, exp4));

        mockMvc.perform(get("/v2/expenses/{monthNo}/summary", 4)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monthlyProgress", is("100%")))
                .andExpect(jsonPath("$.min.value", is(19.78)))
                .andExpect(jsonPath("$.min.expense.id", is(2)))
                .andExpect(jsonPath("$.max.value", is(1000.50)))
                .andExpect(jsonPath("$.max.expense.id", is(4)))
                .andExpect(jsonPath("$.count", is(4)))
                .andExpect(jsonPath("$.average", is(513.38)))
                .andExpect(jsonPath("$.total", is(2053.50)))
                .andExpect(jsonPath("$.totalPaid", is(53.01)))
                .andExpect(jsonPath("$.totalToPay", is(2000.49)));
    }

    @Test
    public void testMonthlySummaryOnlyPaidOnes() throws Exception {
        final Expense exp1 = createSingleExpenseObject();
        exp1.setPaidDate(LocalDate.of(2020, 4, 30));
        exp1.setPaid(true);

        final Expense exp2 = createSingleExpenseObject();
        exp2.setId(2L);
        exp2.setCost(new BigDecimal("19.78"));
        exp1.setPaidDate(LocalDate.of(2020, 4, 30));
        exp2.setPaid(true);

        final Expense exp3 = createSingleExpenseObject();
        exp3.setId(3L);
        exp3.setCost(new BigDecimal("999.99"));

        final Expense exp4 = createSingleExpenseObject();
        exp4.setId(4L);
        exp4.setCost(new BigDecimal("1000.50"));

        when(expenseService.findByMonthAndPaid(4, true)).thenReturn(Arrays.asList(exp1, exp2));

        mockMvc.perform(get("/v2/expenses/{monthNo}/summary?paid=true", 4)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monthlyProgress", is("100%")))
                .andExpect(jsonPath("$.min.value", is(19.78)))
                .andExpect(jsonPath("$.min.expense.id", is(2)))
                .andExpect(jsonPath("$.max.value", is(33.23)))
                .andExpect(jsonPath("$.max.expense.id", is(1)))
                .andExpect(jsonPath("$.count", is(2)))
                .andExpect(jsonPath("$.average", is(26.51)))
                .andExpect(jsonPath("$.total", is(53.01)))
                .andExpect(jsonPath("$.totalPaid", is(53.01)))
                .andExpect(jsonPath("$.totalToPay", is(0)));
    }

    @Test
    public void testMonthlySummaryOnlyToPayOnes() throws Exception {
        final Expense exp1 = createSingleExpenseObject();
        exp1.setPaidDate(LocalDate.of(2020, 4, 30));
        exp1.setPaid(true);

        final Expense exp2 = createSingleExpenseObject();
        exp2.setId(2L);
        exp2.setCost(new BigDecimal("19.78"));
        exp1.setPaidDate(LocalDate.of(2020, 4, 30));
        exp2.setPaid(true);

        final Expense exp3 = createSingleExpenseObject();
        exp3.setId(3L);
        exp3.setCost(new BigDecimal("999.99"));

        final Expense exp4 = createSingleExpenseObject();
        exp4.setId(4L);
        exp4.setCost(new BigDecimal("1000.50"));

        when(expenseService.findByMonthAndPaid(4, false)).thenReturn(Arrays.asList(exp3, exp4));

        mockMvc.perform(get("/v2/expenses/{monthNo}/summary?paid=false", 4)
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monthlyProgress", is("100%")))
                .andExpect(jsonPath("$.min.value", is(999.99)))
                .andExpect(jsonPath("$.min.expense.id", is(3)))
                .andExpect(jsonPath("$.max.value", is(1000.50)))
                .andExpect(jsonPath("$.max.expense.id", is(4)))
                .andExpect(jsonPath("$.count", is(2)))
                .andExpect(jsonPath("$.average", is(1000.25)))
                .andExpect(jsonPath("$.total", is(2000.49)))
                .andExpect(jsonPath("$.totalPaid", is(0)))
                .andExpect(jsonPath("$.totalToPay", is(2000.49)));
    }

    @Test
    public void testMonthlySummaryWithNoExpensesAndMonthProgressIsZero() throws Exception {
        int nextMonth = LocalDate.now().plusMonths(4).getMonthValue();
        when(expenseService.findByMonth(nextMonth)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/v2/expenses/{monthNo}/summary", nextMonth)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monthlyProgress", is("0%")))
                .andExpect(jsonPath("$.count", is(0)))
                .andExpect(jsonPath("$.average", is(0)))
                .andExpect(jsonPath("$.total", is(0)))
                .andExpect(jsonPath("$.totalPaid", is(0)))
                .andExpect(jsonPath("$.totalToPay", is(0)))
                .andExpect(jsonPath("$.min").doesNotExist())
                .andExpect(jsonPath("$.max").doesNotExist());
    }


    private Expense createSingleExpenseObject() {
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setTitle("COPEL - April 2020");
        expense.setDescription("Electricity billl reference to the month of April");
        expense.setCost(new BigDecimal("33.23"));
        expense.setDueDate(LocalDate.of(2020, 4,30));
        expense.setInvoiceDate(LocalDate.of(2020, 4,25));
        expense.setServicePeriodStart(LocalDate.of(2020, 3,25));
        expense.setServicePeriodEnd(LocalDate.of(2020, 4,25));
        expense.setPeriodicity(Periodicity.MONTHLY);
        expense.setPaymentMethod(PaymentMethod.BANK_TRANSFER);
        return expense;
    }

}
