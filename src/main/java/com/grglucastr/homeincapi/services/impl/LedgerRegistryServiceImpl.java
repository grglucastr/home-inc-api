package com.grglucastr.homeincapi.services.impl;

import com.grglucastr.homeincapi.models.LedgerRegistry;
import com.grglucastr.homeincapi.repositories.LedgerRegistryRepository;
import com.grglucastr.homeincapi.services.LedgerRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LedgerRegistryServiceImpl implements LedgerRegistryService {

    @Autowired
    private LedgerRegistryRepository repository;

    @Override
    public List<LedgerRegistry> listActiveFixedIncomeFund(Long userId) {
        return repository.findAllByUserIdAndActiveTrue(userId);
    }

    @Override
    public LedgerRegistry save(LedgerRegistry ledgerRegistry) {
        return repository.save(ledgerRegistry);
    }
}
