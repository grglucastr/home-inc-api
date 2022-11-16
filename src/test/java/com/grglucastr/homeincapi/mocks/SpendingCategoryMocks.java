package com.grglucastr.homeincapi.mocks;

import com.grglucastr.homeincapi.models.SpendingCategory;
import com.grglucastr.homeincapi.models.SpendingCategoryRequest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class SpendingCategoryMocks {

    public static SpendingCategory createSingleSpendingCategory(){
        final SpendingCategory spendingCategory = new com.grglucastr.homeincapi.models.SpendingCategory();
        spendingCategory.setId(1L);
        spendingCategory.setInsertDateTime(LocalDateTime.now());
        spendingCategory.setName("Electricity");
        return spendingCategory;
    }

    public static SpendingCategory createSingleSpendingCategory(Long id){
        final SpendingCategory sc = createSingleSpendingCategory();
        sc.setId(id);
        return sc;
    }

    public static List<SpendingCategory> createListOfActiveSpendingCategories(){
        final SpendingCategory sc1 = createSingleSpendingCategory();
        final SpendingCategory sc2 = createSingleSpendingCategory(2L);
        sc2.setName("Fuel");
        return Arrays.asList(sc1, sc2);
    }
}
