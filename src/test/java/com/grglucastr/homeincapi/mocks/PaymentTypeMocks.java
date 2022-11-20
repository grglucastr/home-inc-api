package com.grglucastr.homeincapi.mocks;

import com.grglucastr.homeincapi.models.PaymentType;
import com.grglucastr.homeincapi.models.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class PaymentTypeMocks {

    public static PaymentType createSinglePaymentType(){
        final PaymentType paymentType = new PaymentType();
        paymentType.setId(1L);
        paymentType.setName("Cash");
        paymentType.setInsertDateTime(LocalDateTime.now());

        final User singleUser = UserMocks.getSingleUser();
        paymentType.setUser(singleUser);
        return paymentType;
    }

    public static PaymentType createSinglePaymentType(Long id){
        final PaymentType singlePaymentType = createSinglePaymentType();
        singlePaymentType.setId(id);
        return singlePaymentType;
    }

    public static List<PaymentType> createListOfActivePaymentType(){
        final PaymentType p1 = createSinglePaymentType();

        final PaymentType p2 = createSinglePaymentType(2L);
        p2.setName("Debit");

        final PaymentType p3 = createSinglePaymentType(3L);
        p3.setName("Credit Card");

        return Arrays.asList(p1, p2, p3);
    }

}
