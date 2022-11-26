package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.enums.Periodicity;
import com.grglucastr.homeincapi.mocks.IncomeMocks;
import com.grglucastr.homeincapi.models.Income;
import com.grglucastr.homeincapi.repositories.IncomeRepository;
import com.grglucastr.homeincapi.services.impl.IncomeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IncomeServiceImplTest {

    private static final long USER_ID = 1234L;

    @Mock
    private IncomeRepository repository;

    @Autowired
    @InjectMocks
    private IncomeServiceImpl service;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testListActiveIncome(){

        final List<Income> listOfActiveIncomes = IncomeMocks.createListOfActiveIncomes();
        when(repository.findAllByUserIdAndActiveTrue(USER_ID)).thenReturn(listOfActiveIncomes);

        final List<Income> incomes = service.listActiveIncomes(USER_ID);

        assertThat(incomes, notNullValue());
        assertThat(incomes, not(empty()));
        assertThat(incomes.size(), equalTo(3));

        assertThat(incomes.get(0).getId(), equalTo(1L));
        assertThat(incomes.get(0).getActive(), is(true));
        assertThat(incomes.get(0).getName(), equalTo("Salary"));
        assertThat(incomes.get(0).getAmount(), equalTo(new BigDecimal(2000)));
        assertThat(incomes.get(0).getCurrencyCode(), equalTo("BRL"));
        assertThat(incomes.get(0).getPeriodicity(), equalTo(Periodicity.MONTHLY));
        assertThat(incomes.get(0).getIncomeCategory(), notNullValue());

        assertThat(incomes.get(1).getId(), equalTo(2L));
        assertThat(incomes.get(1).getActive(), is(true));
        assertThat(incomes.get(1).getName(), equalTo("Overtime"));
        assertThat(incomes.get(1).getAmount(), equalTo(new BigDecimal("1000.44")));
        assertThat(incomes.get(1).getCurrencyCode(), equalTo("BRL"));
        assertThat(incomes.get(1).getPeriodicity(), equalTo(Periodicity.MONTHLY));
        assertThat(incomes.get(1).getIncomeCategory(), notNullValue());

        assertThat(incomes.get(2).getId(), equalTo(3L));
        assertThat(incomes.get(2).getActive(), is(true));
        assertThat(incomes.get(2).getName(), equalTo("API Freelas"));
        assertThat(incomes.get(2).getAmount(), equalTo(new BigDecimal("500.33")));
        assertThat(incomes.get(2).getCurrencyCode(), equalTo("USD"));
        assertThat(incomes.get(2).getPeriodicity(), equalTo(Periodicity.LOOSE));
        assertThat(incomes.get(2).getIncomeCategory(), notNullValue());
    }

    @Test
    void testSaveNewIncome(){
        final Income singleIncome = IncomeMocks.createSingleIncome();
        when(repository.save(any())).thenReturn(singleIncome);

        final Income income = service.save(new Income());

        assertThat(income, notNullValue());
        assertThat(income.getIncomeCategory(), notNullValue());
        assertThat(income.getId(), equalTo(1L));
        assertThat(income.getAmount(), equalTo(new BigDecimal("2000")));
        assertThat(income.getName(), equalTo("Salary"));
        assertThat(income.getCurrencyCode(), equalTo("BRL"));
        assertThat(income.getInsertDateTime(), notNullValue());
        assertThat(income.getUpdateDateTime(), nullValue());
        assertThat(income.getActive(), is(true));
    }
}
