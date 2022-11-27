package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.models.Spending;

import java.util.List;
import java.util.Optional;

public interface SpendingService {
    List<Spending> listActiveSpendings(Long userId);
    Spending save(Spending spending);
    Optional<Spending> findById(Long spendingId);
}
