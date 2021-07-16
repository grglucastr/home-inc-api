package com.grglucastr.homeincapi.service.v2;

import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.model.Income;
import com.grglucastr.homeincapi.IncomeTestObjects;
import com.grglucastr.model.ExpenseMonthlySummaryResponse;
import com.grglucastr.model.ExpenseResponse;
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
    public void testMonthlyReportWithIncomes(){

        final int month = 4;
        final List<Expense> listOfExpenses = createListOfExpenses();
        final Expense minExpense = listOfExpenses.get(1);
        final Expense maxExpense = listOfExpenses.get(3);

        final ExpenseResponse minExpenseResponse = createSingleExpenseResponse(2);
        minExpenseResponse.setCost(minExpense.getCost());

        final ExpenseResponse maxExpenseResponse = createSingleExpenseResponse(4);
        maxExpenseResponse.setCost(maxExpense.getCost());

        when(mapper.map(minExpense, ExpenseResponse.class))
                .thenReturn(minExpenseResponse);

        when(mapper.map(maxExpense, ExpenseResponse.class))
                .thenReturn(maxExpenseResponse);

        final Income income = createIncomeList().get(1);
        when(incomeService.findByDateRange(anyInt(), anyInt())).thenReturn(Optional.of(income));

        final ExpenseMonthlySummaryResponse report = expenseReportService
                .generateSummaryReport(listOfExpenses, month);

        Assert.assertThat(report.getMin(), Matchers.notNullValue());
        Assert.assertThat(report.getMin().getValue(), Matchers.is(new BigDecimal("19.78")));
        Assert.assertThat(report.getMin().getExpense(), Matchers.notNullValue());
        Assert.assertThat(report.getMin().getExpense().getId(), Matchers.is(2));

        Assert.assertThat(report.getMax(), Matchers.notNullValue());
        Assert.assertThat(report.getMax().getValue(), Matchers.is(new BigDecimal("1000.50")));
        Assert.assertThat(report.getMax().getExpense(), Matchers.notNullValue());
        Assert.assertThat(report.getMax().getExpense().getId(), Matchers.is(4));

        Assert.assertThat(report.getCount(), Matchers.is(4));
        Assert.assertThat(report.getAverage(), Matchers.is(new BigDecimal("486.13")));
        Assert.assertThat(report.getTotal(), Matchers.is(new BigDecimal("1944.50")));
        Assert.assertThat(report.getTotalPaid(), Matchers.is(new BigDecimal("53.01")));
        Assert.assertThat(report.getTotalToPay(), Matchers.is(new BigDecimal("1891.49")));
        Assert.assertThat(report.getMonthlyProgress(), Matchers.is("100%"));

        Assert.assertThat(report.getMonthlyIncome(), Matchers.is(new BigDecimal("4022")));
    }

    @Test
    public void testGenerateReportButMonthHasNoExpensesRecorded(){

        final int month = 4;
        final ExpenseMonthlySummaryResponse report = expenseReportService
                .generateSummaryReport(Collections.emptyList(), month);

        Assert.assertThat(report.getMonthlyProgress(), Matchers.is("100%"));
        Assert.assertThat(report.getCount(), Matchers.is(0));
        Assert.assertThat(report.getTotal(), Matchers.is(BigDecimal.ZERO));
        Assert.assertThat(report.getTotalPaid(), Matchers.is(BigDecimal.ZERO));
        Assert.assertThat(report.getTotalToPay(), Matchers.is(BigDecimal.ZERO));
        Assert.assertThat(report.getMonthlyIncome(), Matchers.is(BigDecimal.ZERO));
        Assert.assertThat(report.getMin(), Matchers.is(Matchers.nullValue()));
        Assert.assertThat(report.getMax(), Matchers.is(Matchers.nullValue()));
    }
}