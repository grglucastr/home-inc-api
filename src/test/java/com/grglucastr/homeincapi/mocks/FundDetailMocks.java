package com.grglucastr.homeincapi.mocks;

import com.grglucastr.homeincapi.models.FundDetail;
import com.grglucastr.homeincapi.models.LedgerRegistry;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class FundDetailMocks {

    public static FundDetail createSingleFundDetail(){
        final FundDetail fundDetail = new FundDetail();
        fundDetail.setId(1L);
        fundDetail.setInsertDateTime(MockLocalDate.getInsertDateTime());
        fundDetail.setLastYieldAmount(new BigDecimal("1.35"));
        fundDetail.setDividendYield(new BigDecimal("0.65"));
        fundDetail.setStockPrice(new BigDecimal("189.57"));
        fundDetail.setQuantity(1);
        fundDetail.setPvp(new BigDecimal("0.87"));

        final LedgerRegistry ledgerRegistry = LedgerRegistryMocks.createSingleLedgerRegistry();
        fundDetail.setLedgerRegistry(ledgerRegistry);

        return fundDetail;
    }

    public static FundDetail createSingleFundDetail(Long id){
        final FundDetail singleFundDetail = createSingleFundDetail();
        singleFundDetail.setId(id);
        return singleFundDetail;
    }


    public static List<FundDetail> createListOfActiveFundDetails(){

        final FundDetail fund1 = createSingleFundDetail();
        final FundDetail fund2 = createSingleFundDetail(2L);
        final FundDetail fund3 = createSingleFundDetail(3L);
        fund3.setUpdateDateTime(MockLocalDate.getUpdateDateTime());

        return Arrays.asList(fund1, fund2, fund3);
    }


}
