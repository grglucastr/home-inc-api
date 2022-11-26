package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.mocks.FundDetailMocks;
import com.grglucastr.homeincapi.models.FundDetail;
import com.grglucastr.homeincapi.repositories.FundDetailRepository;
import com.grglucastr.homeincapi.services.impl.FundDetailServiceImpl;
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
public class FundDetailServiceImplTest {

    private static final long USER_ID = 1234L;

    @Mock
    private FundDetailRepository repository;

    @Autowired
    @InjectMocks
    private FundDetailServiceImpl service;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testListAllActiveFundDetails(){

        final List<FundDetail> activeFundDetails = FundDetailMocks.createListOfActiveFundDetails();
        when(repository.findAllByUserIdAndActiveTrue(USER_ID)).thenReturn(activeFundDetails);

        final List<FundDetail> fundDetails = service.listActiveIncomeCategories(USER_ID);

        assertThat(fundDetails, notNullValue());
        assertThat(fundDetails.size(), is(3));
        assertThat(fundDetails.get(0).getActive(), is(true));
        assertThat(fundDetails.get(0).getId(), equalTo(1L));

        assertThat(fundDetails.get(1).getActive(), is(true));
        assertThat(fundDetails.get(1).getId(), equalTo(2L));

        assertThat(fundDetails.get(2).getActive(), is(true));
        assertThat(fundDetails.get(2).getId(), equalTo(3L));
    }

    @Test
    void testAddFundDetail(){
        final FundDetail singleFundDetail = FundDetailMocks.createSingleFundDetail();
        when(repository.save(any())).thenReturn(singleFundDetail);

        final FundDetail fundDetail = service.save(new FundDetail());

        assertThat(fundDetail, notNullValue());
        assertThat(fundDetail.getId(), is(1L));
        assertThat(fundDetail.getInsertDateTime(), notNullValue());
        assertThat(fundDetail.getUpdateDateTime(), nullValue());
        assertThat(fundDetail.getActive(), is(true));
        assertThat(fundDetail.getLastYieldAmount(), equalTo(new BigDecimal("1.35")));
        assertThat(fundDetail.getDividendYield(), equalTo(new BigDecimal("0.65")));
        assertThat(fundDetail.getStockPrice(), equalTo(new BigDecimal("189.57")));
        assertThat(fundDetail.getQuantity(), is(1));
        assertThat(fundDetail.getPvp(), equalTo(new BigDecimal("0.87")));

        assertThat(fundDetail.getLedgerRegistry(), notNullValue());
    }
}
