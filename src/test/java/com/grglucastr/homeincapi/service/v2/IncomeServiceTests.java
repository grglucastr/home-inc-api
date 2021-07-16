package com.grglucastr.homeincapi.service.v2;

import com.grglucastr.homeincapi.model.Income;
import com.grglucastr.homeincapi.repository.IncomeRepository;
import com.grglucastr.homeincapi.IncomeTestObjects;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IncomeServiceTests extends IncomeTestObjects {

    @Mock
    private IncomeRepository repository;

    @InjectMocks
    @Autowired
    private IncomeServiceImpl incomeService;

    @Test
    public void testSaveIncome() {
        final Income income = createSingleIncome();

        when(repository.save(any())).thenReturn(income);

        final Income incomeSaved = incomeService.save(new Income());

        assertAllIncomeAttributes(income, incomeSaved);
    }

    @Test
    public void testFindAllIncomes(){
        final List<Income> incomeList = createIncomeList();

        when(repository.findAll()).thenReturn(incomeList);
        final List<Income> all = incomeService.findAll();

        Assert.assertThat(all.size(), Matchers.is(incomeList.size()));
    }

    @Test
    public void testFindById() {
        final Income singleIncome = createSingleIncome();

        when(repository.findById(anyLong())).thenReturn(Optional.of(singleIncome));

        final Optional<Income> found = incomeService.findById(anyLong());

        Assert.assertThat(found.isPresent(), Matchers.is(true));
        assertAllIncomeAttributes(singleIncome, found.get());
    }

    @Test
    public void testFindByIdButIncomeIsNotFound(){
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        final Optional<Income> found = incomeService.findById(anyLong());
        Assert.assertThat(found.isPresent(), Matchers.is(false));
    }

    @Test
    public void testFindByDateRange() {
        final Income singleIncome = createSingleIncome();
        final int year = 2020;
        final int month = 4;

        when(repository.findByDateRange(any(), any())).thenReturn(Optional.of(singleIncome));

        final Optional<Income> found = incomeService.findByDateRange(year, month);

        Assert.assertThat(found.isPresent(), Matchers.is(true));
        assertAllIncomeAttributes(singleIncome, found.get());
    }

    @Test
    public void testFindByDateRangeButIncomeIsNotFound(){
        final int year = 2020;
        final int month = 4;

        when(repository.findByDateRange(any(), any())).thenReturn(Optional.empty());

        final Optional<Income> found = incomeService.findByDateRange(year, month);
        Assert.assertThat(found.isPresent(), Matchers.is(false));
    }

    private void assertAllIncomeAttributes(Income income, Income incomeSaved) {
        Assert.assertThat(incomeSaved.getId(), Matchers.is(income.getId()));
        Assert.assertThat(incomeSaved.getAmount(), Matchers.is(income.getAmount()));
        Assert.assertThat(incomeSaved.getAccountingPeriodStart(), Matchers.is(income.getAccountingPeriodStart()));
        Assert.assertThat(incomeSaved.getAccountingPeriodEnd(), Matchers.is(income.getAccountingPeriodEnd()));
        Assert.assertThat(incomeSaved.getDescription(), Matchers.is(income.getDescription()));
        Assert.assertThat(incomeSaved.getType(), Matchers.is(income.getType()));
        Assert.assertThat(incomeSaved.isActive(), Matchers.is(income.isActive()));
        Assert.assertThat(incomeSaved.getInsertDate(), Matchers.is(income.getInsertDate()));
        Assert.assertThat(incomeSaved.getUpdateDate(), Matchers.is(income.getUpdateDate()));
    }
}
