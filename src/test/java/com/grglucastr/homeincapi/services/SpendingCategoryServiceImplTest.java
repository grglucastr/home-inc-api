package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.mocks.SpendingCategoryMocks;
import com.grglucastr.homeincapi.mocks.UserMocks;
import com.grglucastr.homeincapi.models.SpendingCategory;
import com.grglucastr.homeincapi.models.User;
import com.grglucastr.homeincapi.repositories.SpendingCategoryRepository;
import com.grglucastr.homeincapi.services.impl.SpendingCategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
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
        when(repository.findAllByUserIdAndActiveTrue(USER_ID))
                .thenReturn(SpendingCategoryMocks.createListOfActiveSpendingCategories());

        final List<SpendingCategory> spendingCategories = service.listActiveSpendingCategories(USER_ID);

        assertThat(spendingCategories.size(), equalTo(2));
        assertThat(spendingCategories.get(0).getId(), equalTo(1L));
        assertThat(spendingCategories.get(0).getName(), equalTo("Electricity"));
        assertThat(spendingCategories.get(0).getActive(), equalTo(true));
        assertThat(spendingCategories.get(1).getId(), equalTo(2L));
        assertThat(spendingCategories.get(1).getName(), equalTo("Fuel"));
        assertThat(spendingCategories.get(1).getActive(), equalTo(true));
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

        assertThat(savedSpendingCategory.getActive(), equalTo(true));
        assertThat(savedSpendingCategory.getId(), equalTo(1L));
        assertThat(savedSpendingCategory.getName(), equalTo("Electricity"));
        assertThat(savedSpendingCategory.getInsertDateTime(), notNullValue());
        assertThat(savedSpendingCategory.getUpdateDateTime(), nullValue());
        assertThat(savedSpendingCategory.getUser(), notNullValue());
    }

    @Test
    public void testFindById(){
        final SpendingCategory singleSpendingCategory = SpendingCategoryMocks.createSingleSpendingCategory();
        when(repository.findById(any())).thenReturn(Optional.of(singleSpendingCategory));

        final Optional<SpendingCategory> opSpendingCategory = service.findById(1L);

        assertThat(opSpendingCategory.isPresent(), equalTo(true));

        final SpendingCategory spendingCategory = opSpendingCategory.get();
        assertThat(spendingCategory.getActive(), equalTo(true));
        assertThat(spendingCategory.getId(), equalTo(1L));
        assertThat(spendingCategory.getName(), equalTo("Electricity"));
        assertThat(spendingCategory.getInsertDateTime(), notNullValue());
        assertThat(spendingCategory.getUpdateDateTime(), nullValue());
        assertThat(spendingCategory.getUser(), notNullValue());
    }
}