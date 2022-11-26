package com.grglucastr.homeincapi.mocks;

import com.grglucastr.homeincapi.models.IncomeCategory;
import com.grglucastr.homeincapi.models.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class IncomeCategoryMocks {

    public static List<IncomeCategory> createListActiveIncomeCategories(){

        final IncomeCategory icSalary = createSingleIncomeCategory();

        final IncomeCategory icDividends = createSingleIncomeCategory();
        icDividends.setId(2L);
        icDividends.setName("Dividends");

        final IncomeCategory icOverTime = createSingleIncomeCategory();
        icOverTime.setId(3L);
        icOverTime.setName("Overtime");

        final LocalDateTime updatedDateTime = LocalDateTime.of(2035, 9, 15, 21, 42, 34);
        icOverTime.setUpdateDateTime(updatedDateTime);

        return Arrays.asList(icSalary, icDividends, icOverTime);
    }

    public static IncomeCategory createSingleIncomeCategory(){
        final User singleUser = UserMocks.getSingleUser();
        final IncomeCategory incomeCategory = new IncomeCategory("Salary", singleUser);
        incomeCategory.setId(1L);

        final LocalDateTime insertDateTime = LocalDateTime.of(2035, 9, 10, 14, 21, 34);
        incomeCategory.setInsertDateTime(insertDateTime);
        return incomeCategory;
    }

}
