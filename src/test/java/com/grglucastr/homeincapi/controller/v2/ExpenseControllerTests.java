package com.grglucastr.homeincapi.controller.v2;

import com.grglucastr.homeincapi.TestObjects;
import com.grglucastr.homeincapi.enums.PaymentMethod;
import com.grglucastr.homeincapi.enums.Periodicity;
import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.service.v2.ExpenseReportService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(controllers = ExpenseController.class)
public class ExpenseControllerTests extends TestObjects {

    private static final String NEW_EXPENSE_PAYLOAD_JSON = "new-expense-payload.json";
    private static final String PATCH_EXPENSE_PAYLOAD = "patch-expense-payload.json";
    private static final String URL_V2_EXPENSES = "/v2/expenses";
    private static final String URL_V2_SUMMARY_YEAR_MONTH = "/v2/expenses/year/{year}/month/{month}/summary";
    private static final String NEW_EXPENSE_ONCE_PERIODICITY_PAYLOAD_JSON = "/new-expense-once-periodicity-payload.json";
    private static final String NEW_EXPENSE_PIX_PAYMENT_METHOD_PAYLOAD_JSON = "/new-expense-pix-payment-method-payload.json";

    private ExpenseReportService expenseReportService;
    private ExpenseService expenseService;
    private ModelMapper modelMapper;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        expenseService = mock(ExpenseService.class);
        expenseReportService = mock(ExpenseReportService.class);
        modelMapper = new ModelMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(new ExpenseController(expenseService, expenseReportService, modelMapper)).build();
    }

    @Test
    public void getExpensesTest() throws Exception {

        final Expense expense1 = createSingleExpenseObject();
        final Expense expense2 = createSingleExpenseObject(2L);
        final Expense expense3 = createSingleExpenseObject(3L);

        List<Expense> expenses = Arrays.asList(expense1, expense2, expense3);
        when(expenseService.findAll()).thenReturn(expenses);

        mockMvc.perform(get(URL_V2_EXPENSES))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].isActive", is(true)))
                .andExpect(jsonPath("$.[0].paid", is(false)))
                .andExpect(jsonPath("$.[0]._links").exists())
                .andExpect(jsonPath("$.[0]._links").isArray())
                .andExpect(jsonPath("$.[0]._links", hasSize(1)))
                .andExpect(jsonPath("$.[0]._links[0].rel", is("self")))
                .andExpect(jsonPath("$.[0]._links[0].title", is("Mark as Paid")))
                .andExpect(jsonPath("$.[0]._links[0].href", is("http://localhost/v2/expenses/1/pay")))
                .andExpect(jsonPath("$.[1]._links").exists())
                .andExpect(jsonPath("$.[1]._links").isArray())
                .andExpect(jsonPath("$.[1]._links", hasSize(1)))
                .andExpect(jsonPath("$.[1]._links[0].rel", is("self")))
                .andExpect(jsonPath("$.[1]._links[0].title", is("Mark as Paid")))
                .andExpect(jsonPath("$.[1]._links[0].href", is("http://localhost/v2/expenses/2/pay")))
                .andExpect(jsonPath("$.[2]._links").exists())
                .andExpect(jsonPath("$.[2]._links").isArray())
                .andExpect(jsonPath("$.[2]._links", hasSize(1)))
                .andExpect(jsonPath("$.[2]._links[0].rel", is("self")))
                .andExpect(jsonPath("$.[2]._links[0].title", is("Mark as Paid")))
                .andExpect(jsonPath("$.[2]._links[0].href", is("http://localhost/v2/expenses/3/pay")));
    }

    @Test
    public void getPaidExpensesOnlyTest() throws Exception {
        final Expense exp1 = createSingleExpenseObject(1L);
        final Expense exp2 = createSingleExpenseObject(2L);
        final Expense exp3 = createSingleExpenseObject(3L);

        exp1.setPaid(true);
        exp2.setPaid(false);
        exp3.setPaid(true);

        List<Expense> expenses = Arrays.asList(exp1, exp2, exp3);

        when(expenseService.findAll()).thenReturn(expenses);

        final MockHttpServletRequestBuilder get = get(URL_V2_EXPENSES)
                .param("paid", "true");

        mockMvc.perform(get)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$.[0].paid", is(true)));
    }

    @Test
    public void getDailyExpensesOnlyTest() throws Exception {
        final Expense exp1 = createSingleExpenseObject(1L);
        final Expense exp2 = createSingleExpenseObject(2L);
        final Expense exp3 = createSingleExpenseObject(3L);

        exp1.setPaid(true);
        exp1.setPeriodicity(Periodicity.DAILY);

        exp2.setPaid(false);
        exp3.setPaid(true);

        List<Expense> expenses = Arrays.asList(exp1, exp2, exp3);

        when(expenseService.findAll()).thenReturn(expenses);

        final MockHttpServletRequestBuilder get = get(URL_V2_EXPENSES)
                .param("periodicity", "daily");
        mockMvc.perform(get)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$.[0].id", is(1)));
    }

    @Test
    public void getExpensesWithCashAsPaymentMethodTest() throws Exception{
        final Expense exp1 = createSingleExpenseObject(1L);
        final Expense exp2 = createSingleExpenseObject(2L);
        final Expense exp3 = createSingleExpenseObject(3L);

        exp1.setPaid(true);
        exp2.setPaid(false);
        exp3.setPaid(true);
        exp3.setPaymentMethod(PaymentMethod.CASH);

        List<Expense> expenses = Arrays.asList(exp1, exp2, exp3);
        when(expenseService.findAll()).thenReturn(expenses);

        final MockHttpServletRequestBuilder get = get(URL_V2_EXPENSES)
                .param("paymentMethod", "cash");
        mockMvc.perform(get)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$.[0].id", is(3)));
    }

    @Test
    public void getExpensesWithDateRangeTest() throws Exception{

        final Expense exp1 = createSingleExpenseObject(1L);
        final Expense exp2 = createSingleExpenseObject(2L);
        final Expense exp3 = createSingleExpenseObject(3L);
        final Expense exp4 = createSingleExpenseObject(3L);

        exp1.setDueDate(LocalDate.of(2020, 3, 14));
        exp2.setDueDate(LocalDate.of(2020, 3, 20));
        exp3.setDueDate(LocalDate.of(2020, 3, 28));
        exp4.setDueDate(LocalDate.of(2020, 3, 30));

        List<Expense> expenses = Arrays.asList(exp1, exp2, exp3, exp4);
        when(expenseService.findAll()).thenReturn(expenses);

        final MockHttpServletRequestBuilder get = get(URL_V2_EXPENSES)
                .param("dueDateStart", "2020-03-20")
                .param("dueDateEnd", "2020-03-30");

        mockMvc.perform(get)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)));
    }


    @Test
    public void postExpensesTest() throws Exception {

        final Expense expense = createSingleExpenseObject();
        expense.setId(333L);

        when(expenseService.save(any(Expense.class))).thenReturn(expense);

        InputStream is = ExpenseControllerTests.class.getResourceAsStream("/" + NEW_EXPENSE_PAYLOAD_JSON);
        assert is != null : NEW_EXPENSE_PAYLOAD_JSON + " not found.";

        mockMvc.perform(post(URL_V2_EXPENSES)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(is.readAllBytes()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(333)))
                .andExpect(jsonPath("$.cost", is(33.23)))
                .andExpect(jsonPath("$._links").exists())
                .andExpect(jsonPath("$._links").isArray())
                .andExpect(jsonPath("$._links", hasSize(1)))
                .andExpect(jsonPath("$._links[0].rel", is("self")))
                .andExpect(jsonPath("$._links[0].title", is("Mark as Paid")))
                .andExpect(jsonPath("$._links[0].href", is("http://localhost/v2/expenses/333/pay")))
                .andExpect(jsonPath("$.typableLine").exists())
                .andExpect(jsonPath("$.typableLine", is("0341.00000 00000.000000 00000.000000 0 00000000000001")));
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
                .andExpect(jsonPath("$.paymentMethod", is(PaymentMethod.BANK_TRANSFER.toString().toLowerCase())))
                .andExpect(jsonPath("$._links").exists())
                .andExpect(jsonPath("$._links").isArray())
                .andExpect(jsonPath("$._links[0].rel").exists())
                .andExpect(jsonPath("$._links[0].rel", is("self")))
                .andExpect(jsonPath("$._links[0].title").exists())
                .andExpect(jsonPath("$._links[0].title", is("Mark as Paid")))
                .andExpect(jsonPath("$._links[0].href").exists())
                .andExpect(jsonPath("$._links[0].href", is("http://localhost/v2/expenses/1/pay")))
                .andExpect(jsonPath("$.typableLine").exists())
                .andExpect(jsonPath("$.typableLine", is("0341.00000 00000.000000 00000.000000 0 00000000000001")));
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

        final MockHttpServletRequestBuilder delete = delete("/v2/expenses/{expenseId}/invalidate", 2L)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(delete).andExpect(status().isNotFound());
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
    public void testPatchExpense() throws Exception {

        final Expense expense = createSingleExpenseObject();
        when(expenseService.findById(anyLong())).thenReturn(Optional.of(expense));
        when(expenseService.save(any())).thenReturn(expense);

        final InputStream resourceAsStream = ExpenseControllerTests.class.getResourceAsStream("/" + PATCH_EXPENSE_PAYLOAD);

        assert  resourceAsStream != null : "JSON resource not found";

        final int expenseId = 1;
        final MockHttpServletRequestBuilder patch =
                patch(URL_V2_EXPENSES + "/{expenseId}", expenseId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(resourceAsStream.readAllBytes());

        mockMvc.perform(patch)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.isActive", is(true)))
                .andExpect(jsonPath("$.paidDate[0]", is(2021)))
                .andExpect(jsonPath("$.paidDate[1]", is(10)))
                .andExpect(jsonPath("$.paidDate[2]", is(5)))
                .andExpect(jsonPath("$.title", is("[UPDATED]Expense Title")))
                .andExpect(jsonPath("$.description", is("[UPDATED]Simple description here")))
                .andExpect(jsonPath("$.cost", is(33.11)))
                .andExpect(jsonPath("$.dueDate[0]", is(2021)))
                .andExpect(jsonPath("$.dueDate[1]", is(10)))
                .andExpect(jsonPath("$.dueDate[2]", is(20)))
                .andExpect(jsonPath("$.paid", is(true)))
                .andExpect(jsonPath("$.invoiceDate[0]", is(2021)))
                .andExpect(jsonPath("$.invoiceDate[1]", is(10)))
                .andExpect(jsonPath("$.invoiceDate[2]", is(1)))
                .andExpect(jsonPath("$.servicePeriodStart[0]", is(2021)))
                .andExpect(jsonPath("$.servicePeriodStart[1]", is(9)))
                .andExpect(jsonPath("$.servicePeriodStart[2]", is(25)))
                .andExpect(jsonPath("$.servicePeriodEnd[0]", is(2021)))
                .andExpect(jsonPath("$.servicePeriodEnd[1]", is(10)))
                .andExpect(jsonPath("$.servicePeriodEnd[2]", is(25)))
                .andExpect(jsonPath("$.periodicity", is("just_once")))
                .andExpect(jsonPath("$.paymentMethod", is("ticket")))
                .andExpect(jsonPath("$.typableLine", is("0000.00000 00000.000000 00000.000000 0 00000000000000")))
                ;
    }

    @Test
    public void testExpenseRequestWithOncePeriodicty() throws Exception {

        final Expense expenseResponse = createSingleExpenseObject();
        expenseResponse.setTitle("OTHER - Dinner");
        expenseResponse.setDescription("Super fancy dinner");
        expenseResponse.setCost(new BigDecimal("15000.66"));
        expenseResponse.setPeriodicity(Periodicity.JUST_ONCE);

        when(expenseService.save(any(Expense.class))).thenReturn(expenseResponse);


        final InputStream resourceAsStream = ExpenseControllerTests
                .class.getResourceAsStream(NEW_EXPENSE_ONCE_PERIODICITY_PAYLOAD_JSON);

        assert resourceAsStream != null : "Resource not found.";

        final MockHttpServletRequestBuilder post = post(URL_V2_EXPENSES)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(resourceAsStream.readAllBytes());

        mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.periodicity", is("just_once")));
    }

    @Test
    public void testExpenseRequestWithPixPaymentMethod() throws Exception {

        final Expense expenseResponse = createSingleExpenseObject();
        expenseResponse.setTitle("OTHER - Dinner");
        expenseResponse.setDescription("Super fancy dinner");
        expenseResponse.setCost(new BigDecimal("15000.66"));
        expenseResponse.setPeriodicity(Periodicity.JUST_ONCE);
        expenseResponse.setPaymentMethod(PaymentMethod.PIX);

        when(expenseService.save(any(Expense.class))).thenReturn(expenseResponse);


        final InputStream resourceAsStream = ExpenseControllerTests
                .class.getResourceAsStream(NEW_EXPENSE_PIX_PAYMENT_METHOD_PAYLOAD_JSON);

        assert resourceAsStream != null : "Resource not found.";

        final MockHttpServletRequestBuilder post = post(URL_V2_EXPENSES)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(resourceAsStream.readAllBytes());

        mockMvc.perform(post)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("OTHER - Dinner")))
                .andExpect(jsonPath("$.description", is("Super fancy dinner")))
                .andExpect(jsonPath("$.cost", is(15000.66)))
                .andExpect(jsonPath("$.periodicity", is("just_once")))
                .andExpect(jsonPath("$.paymentMethod", is("pix")));
    }

}
