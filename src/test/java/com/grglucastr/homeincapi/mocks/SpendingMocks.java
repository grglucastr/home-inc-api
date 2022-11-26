package com.grglucastr.homeincapi.mocks;

import com.grglucastr.homeincapi.models.Spending;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class SpendingMocks {

    public static Spending createSingleSpending(){
        final Spending spending = new Spending();
        spending.setId(1L);
        spending.setName("Copel");
        spending.setDescription("Electricity Bill");
        spending.setCurrencyCode("BRL");
        spending.setInsertDateTime(LocalDateTime.now());
        spending.setSpendingCategory(SpendingCategoryMocks.createSingleSpendingCategory());
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
        spending.setInsertDateTime(LocalDateTime.now());
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

        return Arrays.asList(s1, s2, s3, s4);
    }


}
