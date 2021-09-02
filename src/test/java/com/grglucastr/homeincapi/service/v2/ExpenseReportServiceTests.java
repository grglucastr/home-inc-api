package com.grglucastr.homeincapi.service.v2;

import com.grglucastr.homeincapi.IncomeTestObjects;
import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.model.Income;
import com.grglucastr.model.ExpenseResponse;
import com.grglucastr.model.SingleSummaryResponse;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ExpenseReportServiceTests extends IncomeTestObjects {

    @Mock
    private ModelMapper mapper;

    @Mock
    private IncomeService incomeService;

    @InjectMocks
    @Autowired
    private ExpenseReportServiceImpl expenseReportService;

    @Test
    public void testGenerateReportByYearAndMonth() {

        final List<Expense> listOfExpenses = createListOfExpenses();
        final Expense expensive = listOfExpenses.get(3);
        final Expense cheaper = listOfExpenses.get(1);

        final int year = 2021;
        final int month = 4;

        final ExpenseResponse cheapExpense = createSingleExpenseResponse(2L);
        cheapExpense.setCost(cheaper.getCost());

        final ExpenseResponse expensiveExpense = createSingleExpenseResponse(4L);
        expensiveExpense.setCost(expensive.getCost());

        when(mapper.map(cheaper, ExpenseResponse.class))
                .thenReturn(cheapExpense);

        when(mapper.map(expensive, ExpenseResponse.class))
                .thenReturn(expensiveExpense);

        final Income income = createIncomeList().get(1);
        when(incomeService.findByDateRange(anyInt(), anyInt())).thenReturn(Optional.of(income));

        final SingleSummaryResponse report = expenseReportService
                .generateSingleSummaryReport(listOfExpenses, year, month);


        assertReportAttributes(listOfExpenses, expensive, cheaper, report);
    }

    @Test
    public void testGenerateReportByYearAndMonthButExpenseNotFound(){

        final SingleSummaryResponse report = expenseReportService
                .generateSingleSummaryReport(Collections.emptyList(), 2020, 3);

        Assert.assertThat(report.getMax(), Matchers.is(Matchers.nullValue()));
        Assert.assertThat(report.getMin(), Matchers.is(Matchers.nullValue()));

        Assert.assertThat(report.getMonthlyProgress(), Matchers.is("100%"));
        Assert.assertThat(report.getCount(), Matchers.is(0));
        Assert.assertThat(report.getAverage(), Matchers.is(new BigDecimal("0")));
        Assert.assertThat(report.getTotal(), Matchers.is(new BigDecimal("0")));
        Assert.assertThat(report.getTotalPaid(), Matchers.is(new BigDecimal("0")));
        Assert.assertThat(report.getTotalToPay(), Matchers.is(new BigDecimal("0")));
        Assert.assertThat(report.getMonthlyProgress(), Matchers.is("100%"));
        Assert.assertThat(report.getMonthlyIncome(), Matchers.is(new BigDecimal("0")));
    }

    private void assertReportAttributes(List<Expense> listOfExpenses, Expense expensive, Expense cheaper, SingleSummaryResponse report) {
        Assert.assertThat(report.getMax(), Matchers.is(Matchers.notNullValue()));
        Assert.assertThat(report.getMax().getValue(), Matchers.is(expensive.getCost()));
        Assert.assertThat(report.getMax().getExpense().getId(), Matchers.is(expensive.getId()));

        Assert.assertThat(report.getMin(), Matchers.is(Matchers.notNullValue()));
        Assert.assertThat(report.getMin().getValue(), Matchers.is(cheaper.getCost()));
        Assert.assertThat(report.getMin().getExpense().getId(), Matchers.is(cheaper.getId()));

        Assert.assertThat(report.getMonthlyProgress(), Matchers.is("100%"));
        Assert.assertThat(report.getCount(), Matchers.is(listOfExpenses.size()));
        Assert.assertThat(report.getAverage(), Matchers.is(new BigDecimal("486.13")));
        Assert.assertThat(report.getTotal(), Matchers.is(new BigDecimal("1944.50")));
        Assert.assertThat(report.getTotalPaid(), Matchers.is(new BigDecimal("53.01")));
        Assert.assertThat(report.getTotalToPay(), Matchers.is(new BigDecimal("1891.49")));
        Assert.assertThat(report.getMonthlyProgress(), Matchers.is("100%"));
        Assert.assertThat(report.getMonthlyIncome(), Matchers.is(new BigDecimal("4022")));
    }
}