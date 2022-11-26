package com.grglucastr.homeincapi.services.impl;

import com.grglucastr.homeincapi.models.FixedIncomeFund;
import com.grglucastr.homeincapi.repositories.FixedIncomeFundRepository;
import com.grglucastr.homeincapi.services.FixedIncomeFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FixedIncomeFundServiceImpl implements FixedIncomeFundService {

    @Autowired
    private FixedIncomeFundRepository repository;

    @Override
    public List<FixedIncomeFund> listActiveFixedIncomeFund(Long userId) {
        return repository.findAllByUserIdAndActiveTrue(userId);
    }

    @Override
    public FixedIncomeFund save(FixedIncomeFund fixedIncomeFund) {
        return repository.save(fixedIncomeFund);
    }
}
