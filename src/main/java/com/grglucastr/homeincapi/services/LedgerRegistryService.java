package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.models.LedgerRegistry;

import java.util.List;
import java.util.Optional;

public interface LedgerRegistryService {
    List<LedgerRegistry> listActiveLedgerRegistriesBySpendingId(Long spendingId);
    LedgerRegistry save(LedgerRegistry ledgerRegistry);
    Optional<LedgerRegistry> findById(Long ledgerRegistryId);
}
