package com.grglucastr.homeincapi.controller.v2;


import com.grglucastr.homeincapi.TestObjects;
import com.grglucastr.homeincapi.service.v2.ExpenseReportService;
import com.grglucastr.homeincapi.service.v2.ExpenseService;
import com.grglucastr.model.SingleSummaryResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(controllers = SummaryController.class)
public class SummaryControllerTests extends TestObjects {

    private static final String SUMMARY_URL = "/v2/summary";
    private static final String SINGLE_SUMMARY_URL = SUMMARY_URL + "/year/2020/month/1";
    public static final int YEAR = 2020;
    public static final int MONTH = 1;

    private MockMvc mockMvc;
    private ExpenseService expenseService;
    private ExpenseReportService expenseReportService;

    @Before
    public void setUp() {
        expenseService = mock(ExpenseService.class);
        expenseReportService = mock(ExpenseReportService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new SummaryController(expenseService, expenseReportService)).build();
    }

    @Test
    public void testSummaryListingEndpoints() throws Exception {

        final MockHttpServletRequestBuilder get = get(SUMMARY_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final Map<String, String> summaryLinks = getSummaryLinks();

        mockMvc.perform(get)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.previousMonth", is(summaryLinks.get("previousMonth"))))
                .andExpect(jsonPath("$.currentMonth", is(summaryLinks.get("currentMonth"))))
                .andExpect(jsonPath("$.nextMonth", is(summaryLinks.get("nextMonth"))));
    }

    @Test
    public void testGetSingleSummary() throws Exception {

        final MockHttpServletRequestBuilder get = get(SINGLE_SUMMARY_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        when(expenseService.findByMonthAndYear(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(expenseReportService.generateSingleSummaryReport(anyList(), anyInt(), anyInt()))
                .thenReturn(createSingleSummaryResponse());

        final Map<String, String> summaryLinks = getSummaryLinks(2020, 1);

        mockMvc.perform(get)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monthlyProgress", is("100%")))
                .andExpect(jsonPath("$.min").exists())
                .andExpect(jsonPath("$.min").isNotEmpty())
                .andExpect(jsonPath("$.min.value", is(19.78)))
                .andExpect(jsonPath("$.min.expense").exists())
                .andExpect(jsonPath("$.min.expense").isNotEmpty())
                .andExpect(jsonPath("$.min.expense.id", is(2)))
                .andExpect(jsonPath("$.min.expense.cost", is(19.78)))
                .andExpect(jsonPath("$.max").exists())
                .andExpect(jsonPath("$.max").isNotEmpty())
                .andExpect(jsonPath("$.max.value", is(1000.50)))
                .andExpect(jsonPath("$.max.expense").exists())
                .andExpect(jsonPath("$.max.expense").isNotEmpty())
                .andExpect(jsonPath("$.max.expense.id", is(4)))
                .andExpect(jsonPath("$.max.expense.cost", is(1000.50)))
                .andExpect(jsonPath("$.count", is(4)))
                .andExpect(jsonPath("$.average", is(513.38)))
                .andExpect(jsonPath("$.total", is(2053.50)))
                .andExpect(jsonPath("$.totalPaid", is(53.01)))
                .andExpect(jsonPath("$.totalToPay", is(2000.49)))
                .andExpect(jsonPath("$.monthlyIncome", is(15234.90)))
                .andExpect(jsonPath("$.previousMonth").exists())
                .andExpect(jsonPath("$.previousMonth", is(summaryLinks.get("previousMonth"))))
                .andExpect(jsonPath("$.nextMonth").exists())
                .andExpect(jsonPath("$.nextMonth", is(summaryLinks.get("nextMonth"))));
    }

    @Test
    public void testMonthlySummaryWithNoExpensesAndMonthProgressIsZero() throws Exception {
        final int year = LocalDate.now().getYear();
        final int nextMonth = LocalDate.now().plusMonths(4).getMonthValue();

        final SingleSummaryResponse monthlyResponse = createMonthlyResponseWithZero();

        when(expenseReportService.generateSingleSummaryReport(anyList(), anyInt(), anyInt()))
                .thenReturn(monthlyResponse);

        mockMvc.perform(get(SINGLE_SUMMARY_URL, year, nextMonth)
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

    private Map<String, String> getSummaryLinks(){

        final int year = LocalDate.now().getYear();
        final int month = LocalDate.now().getMonthValue();
        return getSummaryLinks(year, month);
    }

    private Map<String, String> getSummaryLinks(int year, int month){

        final LocalDate paramDate = LocalDate.of(year, month, 1);

        final int prevYear = paramDate.minusMonths(1L).getYear();
        final int prevMonth = paramDate.minusMonths(1L).getMonthValue();

        final int currentYear = paramDate.getYear();
        final int currentMonth = paramDate.getMonthValue();

        final int nextYear = paramDate.plusMonths(1L).getYear();
        final int nextMonth = paramDate.plusMonths(1L).getMonthValue();

        final HashMap<String, String> links = new HashMap<>();
        links.put("previousMonth", "http://localhost/v2/summary/year/"+prevYear+"/month/"+prevMonth);
        links.put("currentMonth", "http://localhost/v2/summary/year/"+currentYear+"/month/"+currentMonth);
        links.put("nextMonth", "http://localhost/v2/summary/year/"+nextYear+"/month/"+nextMonth);

        return links;
    }

}
