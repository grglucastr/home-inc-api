package com.grglucastr.homeincapi.services.impl;

import com.grglucastr.homeincapi.mocks.SpendingCategoryMocks;
import com.grglucastr.homeincapi.mocks.UserMocks;
import com.grglucastr.homeincapi.models.SpendingCategory;
import com.grglucastr.homeincapi.models.User;
import com.grglucastr.homeincapi.repositories.SpendingCategoryRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SpendingCategoryServiceImplTest {

    private static final long USER_ID = 82819L;

    @Mock
    private SpendingCategoryRepository repository;

    @InjectMocks
    @Autowired
    private SpendingCategoryServiceImpl service;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testListActiveSpendingCategories() {
        when(repository.findAllByActiveTrue())
                .thenReturn(SpendingCategoryMocks.createListOfActiveSpendingCategories());

        final List<SpendingCategory> spendingCategories = service.listActiveSpendingCategories(USER_ID);

        assertThat(spendingCategories.size(), Matchers.equalTo(2));
        assertThat(spendingCategories.get(0).getId(), Matchers.equalTo(1L));
        assertThat(spendingCategories.get(0).getName(), Matchers.equalTo("Electricity"));
        assertThat(spendingCategories.get(0).getActive(), Matchers.equalTo(true));
        assertThat(spendingCategories.get(1).getId(), Matchers.equalTo(2L));
        assertThat(spendingCategories.get(1).getName(), Matchers.equalTo("Fuel"));
        assertThat(spendingCategories.get(1).getActive(), Matchers.equalTo(true));
    }

    @Test
    void testSave() {

        final SpendingCategory newSpendingCategory = SpendingCategoryMocks.createSingleSpendingCategory();
        newSpendingCategory.setId(null);
        newSpendingCategory.setInsertDateTime(null);

        final User singleUser = UserMocks.getSingleUser();
        final SpendingCategory sc = SpendingCategoryMocks.createSingleSpendingCategory();
        sc.setUser(singleUser);
        when(repository.save(any())).thenReturn(sc);

        final SpendingCategory savedSpendingCategory = service.save(newSpendingCategory);

        assertThat(savedSpendingCategory.getActive(), Matchers.equalTo(true));
        assertThat(savedSpendingCategory.getId(), Matchers.equalTo(1L));
        assertThat(savedSpendingCategory.getName(), Matchers.equalTo("Electricity"));
        assertThat(savedSpendingCategory.getInsertDateTime(), notNullValue());
        assertThat(savedSpendingCategory.getUpdateDateTime(), nullValue());
        assertThat(savedSpendingCategory.getUser(), notNullValue());
    }
}