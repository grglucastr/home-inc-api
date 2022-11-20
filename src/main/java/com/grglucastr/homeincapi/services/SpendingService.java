package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.models.Spending;

import java.util.List;

public interface SpendingService {
    List<Spending> listActiveSpendings();
    Spending save(Spending spending);
}
