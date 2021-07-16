package com.grglucastr.homeincapi.service.v2;

import com.grglucastr.homeincapi.model.Expense;
import com.grglucastr.homeincapi.repository.ExpenseRepository;
import com.grglucastr.homeincapi.TestObjects;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseServiceTests extends TestObjects {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    @Autowired
    private ExpenseServiceImpl expenseService;

    @Test
    public void testSaveExpense() {

        final Expense expense = createSingleExpenseObject();

        when(expenseRepository.save(any())).thenReturn(expense);

        final Expense saved = expenseService.save(new Expense());

        assertExpenseAttributes(expense, saved);
    }

    @Test
    public void testFindExpenseById() {
        final Expense expense = createSingleExpenseObject();

        when(expenseRepository.findById(anyLong())).thenReturn(Optional.of(expense));

        final Optional<Expense> found = expenseService.findById(1L);

        Assert.assertThat(found.isPresent(), Matchers.is(true));
        assertExpenseAttributes(expense, found.get());
    }

    @Test
    public void testFindExpenseByIdButExpenseIsNotFound() {
        when(expenseRepository.findById(anyLong())).thenReturn(Optional.empty());

        final Optional<Expense> found = expenseService.findById(1L);

        Assert.assertThat(found.isPresent(), Matchers.is(false));
    }

    @Test
    public void testListAllExpenses() {

        final List<Expense> listOfExpenses = createListOfExpenses();
        when(expenseRepository.findAll()).thenReturn(listOfExpenses);

        final List<Expense> all = expenseService.findAll();

        Assert.assertThat(all.size(), Matchers.is(listOfExpenses.size()));
    }

    @Test
    public void testListAllExpensesByGivenMonthAndYear() {
        final int year = 2020;
        final int month = 4;

        final List<Expense> listOfExpenses = createListOfExpenses();
        when(expenseRepository.findByMonthAndYearPaid(anyInt(), anyInt())).thenReturn(listOfExpenses);

        final List<Expense> all = expenseService.findByMonthAndYear(year, month);
        Assert.assertThat(all.size(), Matchers.is(listOfExpenses.size()));
    }


    @Test
    public void testListExpensesByMonthAndStatusIsPaid() {
        final List<Expense> rawList = createListOfExpenses();
        final List<Expense> listOfExpenses = rawList.stream().filter(Expense::isPaid).collect(Collectors.toList());
        final int year = 2020;
        final int month = 4;
        final boolean paid = true;

        when(expenseRepository.findByMonthAndYearPaid(anyInt(), anyInt(), anyBoolean())).thenReturn(listOfExpenses);
        final List<Expense> paidOnes = expenseService.findByMonthAndYearAndPaid(year, month, paid);
        Assert.assertThat(paidOnes.size(), Matchers.is(2));
    }

    @Test
    public void testListExpensesByMonthAndStatusIsNotPaid() {
        final List<Expense> rawList = createListOfExpenses();
        final List<Expense> listOfExpenses = rawList.stream().filter(e -> !e.isPaid() ).collect(Collectors.toList());
        final int year = 2020;
        final int month = 4;
        final boolean paid = false;

        when(expenseRepository.findByMonthAndYearPaid(anyInt(), anyInt(), anyBoolean())).thenReturn(listOfExpenses);
        final List<Expense> paidOnes = expenseService.findByMonthAndYearAndPaid(year, month, paid);
        Assert.assertThat(paidOnes.size(), Matchers.is(2));
    }

    private void assertExpenseAttributes(Expense expense, Expense saved) {
        Assert.assertThat(saved.getId(), Matchers.is(expense.getId()));
        Assert.assertThat(saved.getTitle(), Matchers.is(expense.getTitle()));
        Assert.assertThat(saved.getDescription(), Matchers.is(expense.getDescription()));
        Assert.assertThat(saved.getCost(), Matchers.is(expense.getCost()));
        Assert.assertThat(saved.getDueDate(), Matchers.is(expense.getDueDate()));
        Assert.assertThat(saved.isPaid(), Matchers.is(expense.isPaid()));
        Assert.assertThat(saved.getInvoiceDate(), Matchers.is(expense.getInvoiceDate()));
        Assert.assertThat(saved.getServicePeriodEnd(), Matchers.is(expense.getServicePeriodEnd()));
        Assert.assertThat(saved.getServicePeriodStart(), Matchers.is(expense.getServicePeriodStart()));
        Assert.assertThat(saved.getPeriodicity(), Matchers.is(expense.getPeriodicity()));
        Assert.assertThat(saved.getPaymentMethod(), Matchers.is(expense.getPaymentMethod()));
        Assert.assertThat(saved.getIsActive(), Matchers.is(expense.getIsActive()));
        Assert.assertThat(saved.getPaidDate(), Matchers.is(expense.getPaidDate()));
        Assert.assertThat(saved.getInsertDateTime(), Matchers.is(expense.getInsertDateTime()));
        Assert.assertThat(saved.getUpdateDateTime(), Matchers.is(expense.getUpdateDateTime()));
    }


}
