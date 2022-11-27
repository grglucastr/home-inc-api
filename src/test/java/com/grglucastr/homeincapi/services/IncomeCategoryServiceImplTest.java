package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.mocks.IncomeCategoryMocks;
import com.grglucastr.homeincapi.models.IncomeCategory;
import com.grglucastr.homeincapi.repositories.IncomeCategoryRepository;
import com.grglucastr.homeincapi.services.impl.IncomeCategoryServiceImpl;
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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IncomeCategoryServiceImplTest {

    private  static final long USER_ID = 123L;

    @Mock
    private IncomeCategoryRepository incomeCategoryRepository;

    @Autowired
    @InjectMocks
    private IncomeCategoryServiceImpl incomeCategoryService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testFindAllActiveIncomeCategories(){
        final List<IncomeCategory> activeIncomeCategories = IncomeCategoryMocks.createListActiveIncomeCategories();
        when(incomeCategoryRepository.findAllByUserIdAndActiveTrue(USER_ID)).thenReturn(activeIncomeCategories);

        final List<IncomeCategory> incomeCategories = incomeCategoryService.listActiveIncomeCategories(USER_ID);

        assertThat(incomeCategories.size(), equalTo(3));
        assertThat(incomeCategories.get(0).getActive(), is(true));
        assertThat(incomeCategories.get(0).getId(), equalTo(1L));
        assertThat(incomeCategories.get(0).getName(), equalTo("Salary"));
        assertThat(incomeCategories.get(0).getInsertDateTime(), notNullValue());

        assertThat(incomeCategories.get(1).getActive(), is(true));
        assertThat(incomeCategories.get(1).getId(), equalTo(2L));
        assertThat(incomeCategories.get(1).getName(), equalTo("Dividends"));
        assertThat(incomeCategories.get(1).getInsertDateTime(), notNullValue());

        assertThat(incomeCategories.get(2).getActive(), is(true));
        assertThat(incomeCategories.get(2).getId(), equalTo(3L));
        assertThat(incomeCategories.get(2).getName(), equalTo("Overtime"));
        assertThat(incomeCategories.get(2).getInsertDateTime(), notNullValue());
        assertThat(incomeCategories.get(2).getUpdateDateTime(), notNullValue());
    }

    @Test
    void testSaveNewIncomeCategory() {

        final IncomeCategory responseIncomeCategory = IncomeCategoryMocks.createSingleIncomeCategory();
        when(incomeCategoryRepository.save(any())).thenReturn(responseIncomeCategory);

        final IncomeCategory requestIncomeCategory = IncomeCategoryMocks.createSingleIncomeCategory();
        requestIncomeCategory.setId(null);
        requestIncomeCategory.setInsertDateTime(null);

        IncomeCategory incomeCategory = incomeCategoryService.save(requestIncomeCategory);
        assertThat(incomeCategory, notNullValue());
        assertThat(incomeCategory.getInsertDateTime(), notNullValue());
        assertThat(incomeCategory.getId(), equalTo(1L));
        assertThat(incomeCategory.getActive(), is(true));
        assertThat(incomeCategory.getUpdateDateTime(), nullValue());
        assertThat(incomeCategory.getName(), equalTo("Salary"));
        assertThat(incomeCategory.getUser(), notNullValue());
        assertThat(incomeCategory.getUser().getId(), notNullValue());
    }

    @Test
    void testFindById() {

        final IncomeCategory mock = IncomeCategoryMocks.createSingleIncomeCategory();
        when(incomeCategoryRepository.findById(any())).thenReturn(Optional.of(mock));

        final Optional<IncomeCategory> opIncomeCategory = incomeCategoryService.findById(1L);

        assertThat(opIncomeCategory.isPresent(), is(true));

        final IncomeCategory incomeCategory = opIncomeCategory.get();
        assertThat(incomeCategory, notNullValue());
        assertThat(incomeCategory.getInsertDateTime(), notNullValue());
        assertThat(incomeCategory.getId(), equalTo(1L));
        assertThat(incomeCategory.getActive(), is(true));
        assertThat(incomeCategory.getUpdateDateTime(), nullValue());
        assertThat(incomeCategory.getName(), equalTo("Salary"));
        assertThat(incomeCategory.getUser(), notNullValue());
        assertThat(incomeCategory.getUser().getId(), notNullValue());
    }
}