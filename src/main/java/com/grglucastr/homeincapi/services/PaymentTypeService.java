package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.models.PaymentType;
import java.util.List;

public interface PaymentTypeService {
    List<PaymentType> listActivePaymentTypes();
    PaymentType save(PaymentType paymentType);
}
