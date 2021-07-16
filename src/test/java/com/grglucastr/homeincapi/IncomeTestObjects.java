package com.grglucastr.homeincapi;

import com.grglucastr.homeincapi.enums.Periodicity;
import com.grglucastr.homeincapi.model.Income;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public abstract class IncomeTestObjects extends TestObjects {

    protected List<Income> createIncomeList() {

        final Income income1 = createSingleIncome();

        final Income income2 = createSingleIncome(2L,new BigDecimal("4022"));
        income2.setAccountingPeriodStart(LocalDate.of(2021,3,1));
        income2.setAccountingPeriodEnd(LocalDate.of(2021,5,31));

        final Income income3 = createSingleIncome(3L, new BigDecimal("4062"));
        income3.setAccountingPeriodStart(LocalDate.of(2021,6,1));
        income3.setAccountingPeriodEnd(LocalDate.of(2021,6,30));

        final Income income4 = createSingleIncome(4L, new BigDecimal("4122"));
        income4.setAccountingPeriodStart(LocalDate.of(2021,7,1));
        income4.setAccountingPeriodEnd(null);
        income4.setActive(true);

        return Arrays.asList(income1, income2, income3, income4);
    }

    protected Income createSingleIncome(){

        final Income income = new Income();
        income.setId(1l);
        income.setAmount(new BigDecimal("4000"));
        income.setDescription("Salário líquido");
        income.setAccountingPeriodStart(LocalDate.of(2021,1,1));
        income.setAccountingPeriodEnd(LocalDate.of(2021,2,28));
        income.setType(Periodicity.MONTHLY);
        income.setActive(false);

        return income;
    }

    protected Income createSingleIncome(final Long id, final BigDecimal amount){
        final Income singleIncome = createSingleIncome();
        singleIncome.setId(id);
        singleIncome.setAmount(amount);
        return singleIncome;
    }
}

