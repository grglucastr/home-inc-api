package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.mocks.SpendingCategoryMocks;
import com.grglucastr.homeincapi.models.SpendingCategory;
import com.grglucastr.homeincapi.repositories.SpendingCategoryRepository;
import com.grglucastr.homeincapi.services.impl.SpendingCategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(MockitoExtension.class)
class SpendingCategoryServiceTest {

    @Mock
    private SpendingCategoryRepository repository;

    @InjectMocks
    @Autowired
    private SpendingCategoryServiceImpl spendingCategoryService;

    @Test
    void listActiveSpendingCategories() {

        final List<SpendingCategory> list = SpendingCategoryMocks.createListOfActiveSpendingCategories();
        Mockito.when(repository.findAllByActiveTrue()).thenReturn(list);

        final List<SpendingCategory> spendingCategories = spendingCategoryService
                .listActiveSpendingCategories(1L);

        assertThat(spendingCategories, notNullValue());
        assertThat(spendingCategories.size(), is(2));
        assertThat(spendingCategories.get(0).getName(), is("Electricity"));
        assertThat(spendingCategories.get(1).getName(), is("Fuel"));

    }






}