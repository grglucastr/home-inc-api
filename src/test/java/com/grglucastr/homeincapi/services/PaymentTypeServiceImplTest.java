package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.mocks.PaymentTypeMocks;
import com.grglucastr.homeincapi.models.PaymentType;
import com.grglucastr.homeincapi.repositories.PaymentTypeRepository;
import com.grglucastr.homeincapi.services.impl.PaymentTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentTypeServiceImplTest {

    private static final long USER_ID = 1234L;

    @Mock
    private PaymentTypeRepository repository;

    @Autowired
    @InjectMocks
    private PaymentTypeServiceImpl service;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testSave(){
        final PaymentType singlePaymentType = PaymentTypeMocks.createSinglePaymentType();
        when(repository.save(any())).thenReturn(singlePaymentType);

        final PaymentType paymentType = service.save(new PaymentType());

        assertThat(paymentType, notNullValue());
        assertThat(paymentType.getActive(), is(true));
        assertThat(paymentType.getId(), equalTo(1L));
        assertThat(paymentType.getName(), equalTo("Cash"));
        assertThat(paymentType.getInsertDateTime(), notNullValue());
        assertThat(paymentType.getUpdateDateTime(), nullValue());
        assertThat(paymentType.getUser(), notNullValue());
        assertThat(paymentType.getUser().getId(), notNullValue());
        assertThat(paymentType.getUser().getName(), equalTo("Admin 222"));
    }

    @Test
    void testListActivePaymentTypes(){
        final List<PaymentType> listOfActivePaymentTypes = PaymentTypeMocks.createListOfActivePaymentType();

        when(repository.findAllByUserIdAndActiveTrue(USER_ID)).thenReturn(listOfActivePaymentTypes);

        final List<PaymentType> paymentTypes = service.listActivePaymentTypes(USER_ID);

        assertThat(paymentTypes, notNullValue());
        assertThat(paymentTypes.size(), is(3));
        assertThat(paymentTypes.get(0).getActive(), is(true));
        assertThat(paymentTypes.get(0).getId(), is(1L));
        assertThat(paymentTypes.get(0).getName(), equalTo("Cash"));

        assertThat(paymentTypes.get(1).getActive(), is(true));
        assertThat(paymentTypes.get(1).getId(), is(2L));
        assertThat(paymentTypes.get(1).getName(), equalTo("Debit"));

        assertThat(paymentTypes.get(2).getActive(), is(true));
        assertThat(paymentTypes.get(2).getId(), is(3L));
        assertThat(paymentTypes.get(2).getName(), equalTo("Credit Card"));

    }
}
