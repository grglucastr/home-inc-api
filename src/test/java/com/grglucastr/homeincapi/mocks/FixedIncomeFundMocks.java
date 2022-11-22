package com.grglucastr.homeincapi.mocks;

import com.grglucastr.homeincapi.models.FixedIncomeFund;
import com.grglucastr.homeincapi.models.SpendingCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class FixedIncomeFundMocks {

    public static FixedIncomeFund createSingleFixedIncomeFund(){
        final FixedIncomeFund fixedIncomeFund = new FixedIncomeFund();
        fixedIncomeFund.setDueDate(LocalDate.of(2025, 1,1));
        fixedIncomeFund.setAnnualProfitPercentage(new BigDecimal("11.2"));
        fixedIncomeFund.setProductPrice(new BigDecimal("1234.90"));
        fixedIncomeFund.setMinAmountAllowed(new BigDecimal("12.90"));

        fixedIncomeFund.setId(1L);
        fixedIncomeFund.setName("Tesouro Prefixado 2025");
        fixedIncomeFund.setDescription("Investimento em renda fixa, tesouro prefixado.");
        fixedIncomeFund.setInsertDateTime(LocalDateTime.now());

        final SpendingCategory spendingCategory = SpendingCategoryMocks.createSingleSpendingCategory();
        fixedIncomeFund.setSpendingCategory(spendingCategory);
        return fixedIncomeFund;
    }

    public static List<FixedIncomeFund> createListOfAllActiveFixedIncomeFund(){
        final FixedIncomeFund ff1 = createSingleFixedIncomeFund();

        final FixedIncomeFund ff2 = createSingleFixedIncomeFund();
        ff2.setId(2L);
        ff2.setDueDate(LocalDate.of(2029, 1,1));
        ff2.setName("Tesouro Prefixado 2029");

        final FixedIncomeFund ff3 = createSingleFixedIncomeFund();
        ff3.setId(3L);
        ff3.setDueDate(LocalDate.of(2024, 1,1));
        ff3.setName("CDB C6 Bank");

        return Arrays.asList(ff1, ff2, ff3);
    }
}
