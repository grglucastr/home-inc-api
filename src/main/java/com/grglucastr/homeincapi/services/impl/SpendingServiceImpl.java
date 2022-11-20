package com.grglucastr.homeincapi.services.impl;

import com.grglucastr.homeincapi.models.Spending;
import com.grglucastr.homeincapi.repositories.SpendingRepository;
import com.grglucastr.homeincapi.services.SpendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpendingServiceImpl implements SpendingService {

    @Autowired
    private SpendingRepository repository;

    @Override
    public List<Spending> listActiveSpendings() {
        return repository.findAllByActiveTrue();
    }

    @Override
    public Spending save(Spending spending) {
        return repository.save(spending);
    }
}
