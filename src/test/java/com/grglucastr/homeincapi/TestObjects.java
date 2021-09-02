package com.grglucastr.homeincapi;

import com.grglucastr.homeincapi.enums.PaymentMethod;
import com.grglucastr.homeincapi.enums.Periodicity;
import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.model.ExpenseResponse;
import com.grglucastr.model.ExpenseSummary;
import com.grglucastr.model.SingleSummaryResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public abstract class TestObjects {

    protected SingleSummaryResponse createSingleSummaryResponse(){
        final ExpenseSummary max = new ExpenseSummary();
        max.setValue(new BigDecimal("1000.50"));

        final ExpenseResponse maxExp = new ExpenseResponse();
        maxExp.setId(4L);
        maxExp.setCost(new BigDecimal("1000.50"));
        max.setExpense(maxExp);

        final ExpenseSummary min = new ExpenseSummary();
        min.setValue(new BigDecimal("19.78"));

        final ExpenseResponse minExp = new ExpenseResponse();
        minExp.setId(2L);
        minExp.setCost(new BigDecimal("19.78"));
        min.setExpense(minExp);

        final SingleSummaryResponse monthlySummary = new SingleSummaryResponse();
        monthlySummary.setMax(max);
        monthlySummary.setMin(min);
        monthlySummary.setCount(4);
        monthlySummary.setAverage(new BigDecimal("513.38"));
        monthlySummary.setTotal(new BigDecimal("2053.50"));
        monthlySummary.setTotalPaid(new BigDecimal("53.01"));
        monthlySummary.setTotalToPay(new BigDecimal("2000.49"));
        monthlySummary.setMonthlyIncome(new BigDecimal("15234.90"));
        monthlySummary.setMonthlyProgress("100%");


        return monthlySummary;
    }

    protected SingleSummaryResponse createMonthlyResponseWithZero(){
        final SingleSummaryResponse monthlyResponse = createSingleSummaryResponse();

        monthlyResponse.setAverage(BigDecimal.ZERO);
        monthlyResponse.setTotalToPay(BigDecimal.ZERO);
        monthlyResponse.setTotal(BigDecimal.ZERO);
        monthlyResponse.setTotalPaid(BigDecimal.ZERO);
        monthlyResponse.setCount(0);
        monthlyResponse.setMonthlyProgress("0%");
        monthlyResponse.setMin(null);
        monthlyResponse.setMax(null);

        return monthlyResponse;
    }

    protected Expense createSingleExpenseObject() {
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
        expense.setTypableLine("0341.00000 00000.000000 00000.000000 0 00000000000001");
        return expense;
    }

    protected Expense createSingleExpenseObject(Long id) {
        final Expense singleExpenseObject = createSingleExpenseObject();
        singleExpenseObject.setId(id);
        return singleExpenseObject;
    }

    protected List<Expense> createListOfExpenses() {

        final Expense ex1 = createSingleExpenseObject(1L);
        ex1.setPaid(true);

        final Expense ex2 = createSingleExpenseObject(2L);
        ex2.setCost(new BigDecimal("19.78"));
        ex2.setPaid(true);

        final Expense ex3 = createSingleExpenseObject(3L);
        ex3.setCost(new BigDecimal("890.99"));

        final Expense ex4 = createSingleExpenseObject(4L);
        ex4.setCost(new BigDecimal("1000.50"));

        return Arrays.asList(ex1, ex2, ex3, ex4);
    }

    protected ExpenseResponse createSingleExpenseResponse(Long id){
        final ExpenseResponse expenseResponse = new ExpenseResponse();
        expenseResponse.setId(id);
        return expenseResponse;
    }
}
