package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.mocks.SpendingMocks;
import com.grglucastr.homeincapi.models.Spending;
import com.grglucastr.homeincapi.repositories.SpendingRepository;
import com.grglucastr.homeincapi.services.impl.SpendingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SpendingServiceTest {

    private static final long USER_ID = 1234L;

    @Mock
    private SpendingRepository repository;

    @Autowired
    @InjectMocks
    private SpendingServiceImpl service;

    @BeforeEach
    void setUp() {
        
    }

    @Test
    void testListAllActiveSpendings() {

        final List<Spending> activeSpendings = SpendingMocks.createListOfActiveSpendings();
        when(repository.findAllBySpendingCategoryIdAndActiveTrue(USER_ID)).thenReturn(activeSpendings);

        final List<Spending> spendings = service.listActiveSpendings(USER_ID);

        assertThat(spendings, notNullValue());
        assertThat(spendings.size(), equalTo(5));
        assertThat(spendings.get(0).getActive(), is(true));
        assertThat(spendings.get(0).getId(), equalTo(1L));
        assertThat(spendings.get(0).getInsertDateTime(), notNullValue());

        assertThat(spendings.get(1).getActive(), is(true));
        assertThat(spendings.get(1).getId(), equalTo(2L));
        assertThat(spendings.get(1).getInsertDateTime(), notNullValue());

        assertThat(spendings.get(2).getActive(), is(true));
        assertThat(spendings.get(2).getId(), equalTo(3L));
        assertThat(spendings.get(2).getInsertDateTime(), notNullValue());

        assertThat(spendings.get(3).getActive(), is(true));
        assertThat(spendings.get(3).getId(), equalTo(23123L));
        assertThat(spendings.get(3).getInstallments(), equalTo(12));
        assertThat(spendings.get(3).getInsertDateTime(), notNullValue());
    }
    
    @Test
    void testSave(){
        final Spending singleSpending = SpendingMocks.createSingleSpending();
        when(repository.save(any())).thenReturn(singleSpending);

        final Spending spending = service.save(new Spending());

        assertThat(spending, notNullValue());
        assertThat(spending.getId(), is(1L));
        assertThat(spending.getName(), equalTo("Copel"));
        assertThat(spending.getDescription(), equalTo("Electricity Bill"));
        assertThat(spending.getActive(), is(true));
        assertThat(spending.getInsertDateTime(), notNullValue());
        assertThat(spending.getUpdateDateTime(), nullValue());
        assertThat(spending.getInstallments(), nullValue());
        assertThat(spending.getCurrencyCode(), equalTo("BRL"));
        assertThat(spending.getSpendingCategory(), notNullValue());
    }

    @Test
    void testSaveWithInstallments(){
        final Spending singleSpending = SpendingMocks.createSingleSpendingWithInstallments();
        when(repository.save(any())).thenReturn(singleSpending);

        final Spending spending = service.save(new Spending());

        assertThat(spending, notNullValue());
        assertThat(spending.getId(), is(23123L));
        assertThat(spending.getName(), equalTo("Travel to Argentina"));
        assertThat(spending.getDescription(), equalTo("Holidays in Argentina"));
        assertThat(spending.getActive(), is(true));
        assertThat(spending.getInsertDateTime(), notNullValue());
        assertThat(spending.getUpdateDateTime(), notNullValue());
        assertThat(spending.getInstallments(), equalTo(12));
        assertThat(spending.getCurrencyCode(), equalTo("USD"));
        assertThat(spending.getSpendingCategory(), notNullValue());
    }

    @Test
    void testFindById(){
        final Spending singleSpending = SpendingMocks.createSingleSpending();
        when(repository.findById(any())).thenReturn(Optional.of(singleSpending));

        final Optional<Spending> byId = service.findById(1L);

        assertThat(byId.isPresent(), is(true));

        final Spending spending = byId.get();
        assertThat(spending.getId(), is(1L));
        assertThat(spending.getName(), equalTo("Copel"));
        assertThat(spending.getDescription(), equalTo("Electricity Bill"));
        assertThat(spending.getActive(), is(true));
        assertThat(spending.getInsertDateTime(), notNullValue());
        assertThat(spending.getUpdateDateTime(), nullValue());
        assertThat(spending.getInstallments(), nullValue());
        assertThat(spending.getCurrencyCode(), equalTo("BRL"));
        assertThat(spending.getSpendingCategory(), notNullValue());

    }
}
