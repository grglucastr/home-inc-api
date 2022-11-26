package com.grglucastr.homeincapi.services.impl;

import com.grglucastr.homeincapi.models.PaymentType;
import com.grglucastr.homeincapi.repositories.PaymentTypeRepository;
import com.grglucastr.homeincapi.services.PaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentTypeServiceImpl implements PaymentTypeService {

    @Autowired
    private PaymentTypeRepository repository;

    @Override
    public List<PaymentType> listActivePaymentTypes(Long userId) {
        return repository.findAllByUserIdAndActiveTrue(userId);
    }

    @Override
    public PaymentType save(PaymentType paymentType) {
        return repository.save(paymentType);
    }
}
