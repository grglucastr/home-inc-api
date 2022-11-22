package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.mocks.FixedIncomeFundMocks;
import com.grglucastr.homeincapi.mocks.SpendingCategoryMocks;
import com.grglucastr.homeincapi.models.FixedIncomeFund;
import com.grglucastr.homeincapi.repositories.FixedIncomeFundRepository;
import com.grglucastr.homeincapi.services.impl.FixedIncomeFundServiceImpl;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FixedIncomeFundServiceImplTest {

    @Mock
    private FixedIncomeFundRepository repository;

    @Autowired
    @InjectMocks
    private FixedIncomeFundServiceImpl service;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testListAllActiveOnes(){

        final List<FixedIncomeFund> activeOnes = FixedIncomeFundMocks
                .createListOfAllActiveFixedIncomeFund();

        when(repository.findAllByActiveTrue()).thenReturn(activeOnes);

        final List<FixedIncomeFund> fixedIncomeFunds = service.listActiveFixedIncomeFund();

        assertThat(fixedIncomeFunds, notNullValue());
        assertThat(fixedIncomeFunds.size(), equalTo(3));
        assertThat(fixedIncomeFunds.get(0).getId(), equalTo(1L));
        assertThat(fixedIncomeFunds.get(0).getActive(), is(true));
        assertThat(fixedIncomeFunds.get(0).getName(), is("Tesouro Prefixado 2025"));
        assertThat(fixedIncomeFunds.get(0).getSpendingCategory(), notNullValue());

        assertThat(fixedIncomeFunds.get(1).getId(), equalTo(2L));
        assertThat(fixedIncomeFunds.get(1).getActive(), is(true));
        assertThat(fixedIncomeFunds.get(1).getName(), is("Tesouro Prefixado 2029"));
        assertThat(fixedIncomeFunds.get(1).getSpendingCategory(), notNullValue());

        assertThat(fixedIncomeFunds.get(2).getId(), equalTo(3L));
        assertThat(fixedIncomeFunds.get(2).getActive(), is(true));
        assertThat(fixedIncomeFunds.get(2).getName(), is("CDB C6 Bank"));
        assertThat(fixedIncomeFunds.get(2).getSpendingCategory(), notNullValue());
    }

    @Test
    void testSaveFixedIncomeFunds(){

        final FixedIncomeFund fixedIncome = FixedIncomeFundMocks.createSingleFixedIncomeFund();
        when(repository.save(any())).thenReturn(fixedIncome);

        final FixedIncomeFund fixedIncomeFund = service.save(any());

        assertThat(fixedIncomeFund, notNullValue());
        assertThat(fixedIncomeFund.getId(), equalTo(1L));
        assertThat(fixedIncomeFund.getActive(), is(true));
        assertThat(fixedIncomeFund.getInsertDateTime(), notNullValue());
        assertThat(fixedIncomeFund.getUpdateDateTime(), nullValue());
        assertThat(fixedIncomeFund.getSpendingCategory(), notNullValue());

        assertThat(fixedIncomeFund.getName(), equalTo("Tesouro Prefixado 2025"));
        assertThat(fixedIncomeFund.getDescription(), equalTo("Investimento em renda fixa, tesouro prefixado."));
        assertThat(fixedIncomeFund.getAnnualProfitPercentage(), equalTo(new BigDecimal("11.2")));
        assertThat(fixedIncomeFund.getProductPrice(), equalTo(new BigDecimal("1234.90")));
        assertThat(fixedIncomeFund.getMinAmountAllowed(), equalTo(new BigDecimal("12.90")));
        assertThat(fixedIncomeFund.getDueDate(), notNullValue());
    }
}
