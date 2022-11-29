package com.grglucastr.homeincapi.mocks;

import com.grglucastr.homeincapi.enums.Periodicity;
import com.grglucastr.homeincapi.models.FixedIncomeFund;
import com.grglucastr.homeincapi.models.Spending;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class SpendingMocks {

    public static Spending createSingleSpending(){
        final Spending spending = new Spending();
        spending.setId(1L);
        spending.setName("Copel");
        spending.setDescription("Electricity Bill");
        spending.setCurrencyCode("BRL");
        spending.setInsertDateTime(MockLocalDate.getInsertDateTime());
        spending.setSpendingCategory(SpendingCategoryMocks.createSingleSpendingCategory());
        spending.setPeriodicity(Periodicity.MONTHLY);
        return spending;
    }

    public static Spending createSingleSpending(Long id){
        final Spending singleSpending = createSingleSpending();
        singleSpending.setId(id);
        return singleSpending;
    }

    public static Spending createSingleSpendingWithInstallments(){
        final Spending spending = new Spending();
        spending.setId(23123L);
        spending.setName("Travel to Argentina");
        spending.setDescription("Holidays in Argentina");
        spending.setCurrencyCode("USD");
        spending.setInstallments(12);
        spending.setInsertDateTime(MockLocalDate.getInsertDateTime());
        spending.setUpdateDateTime(MockLocalDate.getUpdateDateTime());
        spending.setPeriodicity(Periodicity.MONTHLY);
        spending.setSpendingCategory(SpendingCategoryMocks.createSingleSpendingCategory());
        return spending;
    }

    public static List<Spending> createListOfActiveSpendings(){
        final Spending s1 = createSingleSpending();

        final Spending s2 = createSingleSpending(2L);
        s2.setName("Sanepar");

        final Spending s3 = createSingleSpending(3L);
        s3.setName("Oi");

        final Spending s4 = createSingleSpendingWithInstallments();

        final FixedIncomeFund fixedIncomeFund = createSingleSpendingFixedIncomeFund();

        return Arrays.asList(s1, s2, s3, s4, fixedIncomeFund);
    }

    public static FixedIncomeFund createSingleSpendingFixedIncomeFund() {
        final FixedIncomeFund fixedIncomeFund = new FixedIncomeFund();
        fixedIncomeFund.setId(93371L);
        fixedIncomeFund.setName("Tesouro Prefixado 2029");
        fixedIncomeFund.setDescription("Investimento em Renda Fixa, pre fixado");
        fixedIncomeFund.setCurrencyCode("BRL");
        fixedIncomeFund.setInsertDateTime(MockLocalDate.getInsertDateTime());
        fixedIncomeFund.setUpdateDateTime(MockLocalDate.getUpdateDateTime());
        fixedIncomeFund.setPeriodicity(Periodicity.MONTHLY);
        fixedIncomeFund.setSpendingCategory(SpendingCategoryMocks.createSingleSpendingCategory());
        fixedIncomeFund.setDueDate(LocalDate.of(2029,1,1));
        fixedIncomeFund.setAnnualProfitPercentage(new BigDecimal("13.52"));
        fixedIncomeFund.setProductPrice(new BigDecimal("463.05"));
        fixedIncomeFund.setMinAmountAllowed(new BigDecimal("32.41"));
        return fixedIncomeFund;
    }
}
