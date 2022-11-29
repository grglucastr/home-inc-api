package com.grglucastr.homeincapi.services.impl;

import com.grglucastr.homeincapi.models.LedgerRegistry;
import com.grglucastr.homeincapi.repositories.LedgerRegistryRepository;
import com.grglucastr.homeincapi.services.LedgerRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LedgerRegistryServiceImpl implements LedgerRegistryService {

    @Autowired
    private LedgerRegistryRepository repository;

    @Override
    public List<LedgerRegistry> listActiveLedgerRegistriesBySpendingId(Long spendingId) {
        return repository.findAllByUserIdAndActiveTrue(spendingId);
    }

    @Override
    public LedgerRegistry save(LedgerRegistry ledgerRegistry) {
        return repository.save(ledgerRegistry);
    }

    @Override
    public Optional<LedgerRegistry> findById(Long ledgerRegistryId) {
        return repository.findById(ledgerRegistryId);
    }
}
