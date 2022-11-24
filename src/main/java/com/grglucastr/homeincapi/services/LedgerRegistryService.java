package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.models.LedgerRegistry;

import java.util.List;

public interface LedgerRegistryService {
    List<LedgerRegistry> listActiveFixedIncomeFund();
    LedgerRegistry save(LedgerRegistry ledgerRegistry);
}
