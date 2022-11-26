package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.mocks.LedgerRegistryMocks;
import com.grglucastr.homeincapi.models.LedgerRegistry;
import com.grglucastr.homeincapi.repositories.LedgerRegistryRepository;
import com.grglucastr.homeincapi.services.impl.LedgerRegistryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LedgerRegistryServiceImplTest {

    private static final long USER_ID = 1234L;

    @Mock
    private LedgerRegistryRepository repository;

    @Autowired
    @InjectMocks
    private LedgerRegistryServiceImpl service;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testListAllActiveLedgers(){

        final List<LedgerRegistry> registries = LedgerRegistryMocks.createListOfLedgerRegistries();
        when(repository.findAllByUserIdAndActiveTrue(USER_ID)).thenReturn(registries);

        final List<LedgerRegistry> ledgerRegistries = service.listActiveFixedIncomeFund(USER_ID);

        assertThat(ledgerRegistries, notNullValue());
        assertThat(ledgerRegistries.size(), is(3));
        assertThat(ledgerRegistries.get(0).getActive(), is(true));
        assertThat(ledgerRegistries.get(0).getBillingDate(), equalTo(LocalDate.of(2025, 1, 1)));
        assertThat(ledgerRegistries.get(0).getDueDate(), equalTo(LocalDate.of(2025, 1,15)));
        assertThat(ledgerRegistries.get(0).getAmountDue(), equalTo(new BigDecimal("100.00")));
        assertThat(ledgerRegistries.get(0).getBarCode(), notNullValue());
        assertThat(ledgerRegistries.get(0).getBarCode(), not(emptyString()));
        assertThat(ledgerRegistries.get(0).getQRCode(), notNullValue());
        assertThat(ledgerRegistries.get(0).getQRCode(), not(emptyString()));
        assertThat(ledgerRegistries.get(0).getPaymentType(), notNullValue());
        assertThat(ledgerRegistries.get(0).getSpending(), notNullValue());

        assertThat(ledgerRegistries.get(1).getActive(), is(true));
        assertThat(ledgerRegistries.get(2).getActive(), is(true));

    }

    @Test
    void testSaveNewRegisterToTheLedger(){

        final LedgerRegistry ledgerRegistry = LedgerRegistryMocks.createSingleLedgerRegistry();
        when(repository.save(any())).thenReturn(ledgerRegistry);

        final LedgerRegistry registry = service.save(new LedgerRegistry());

        assertThat(registry, notNullValue());
        assertThat(registry.getId(), equalTo(1L));
        assertThat(registry.getActive(), is(true));
        assertThat(registry.getInsertDateTime(), notNullValue());
        assertThat(registry.getUpdateDateTime(), nullValue());
        assertThat(registry.getAmountDue(), equalTo(new BigDecimal("100.00")));
        assertThat(registry.getBarCode(), equalTo("82670000000-1 90700109202-8 21015332000-2 13102022419-5"));
        assertThat(registry.getQRCode(), equalTo("lorem ipsum dolor sit amet"));
        assertThat(registry.getSpending(), notNullValue());
        assertThat(registry.getPaymentType(), notNullValue());
        assertThat(registry.getPaid(), is(false));
    }
}
