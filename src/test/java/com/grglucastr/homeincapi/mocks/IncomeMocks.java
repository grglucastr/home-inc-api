package com.grglucastr.homeincapi.mocks;

import com.grglucastr.homeincapi.enums.Periodicity;
import com.grglucastr.homeincapi.models.Income;
import com.grglucastr.homeincapi.models.IncomeCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class IncomeMocks {

    public static Income createSingleIncome(){

        final IncomeCategory incomeCategory = IncomeCategoryMocks.createSingleIncomeCategory();

        final Income income = new Income();
        income.setIncomeCategory(incomeCategory);
        income.setAmount(new BigDecimal(2000));
        income.setName("Salary");
        income.setCurrencyCode("BRL");
        income.setPeriodicity(Periodicity.MONTHLY);
        income.setId(1L);
        income.setInsertDateTime(LocalDateTime.now());
        return income;
    }

    public static Income createSingleIncome(Long id){
        final Income income = createSingleIncome();
        income.setId(id);
        return income;
    }

    public static Income createSingleIncome(Long id, String name, String currencyCode){
        final Income income = createSingleIncome(id);
        income.setName(name);
        income.setCurrencyCode(currencyCode);
        return income;
    }

    public static List<Income> createListOfActiveIncomes(){
        final Income inc1 = createSingleIncome();
        final Income inc2 = createSingleIncome(2L, "Overtime", "BRL");
        inc2.setAmount(new BigDecimal("1000.44"));

        final Income inc3 = createSingleIncome(3L, "API Freelas", "USD");
        inc3.setAmount(new BigDecimal("500.33"));
        inc3.setPeriodicity(Periodicity.LOOSE);

        return Arrays.asList(inc1, inc2, inc3);
    }

}
