package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.models.FixedIncomeFund;

import java.util.List;

public interface FixedIncomeFundService {
    List<FixedIncomeFund> listActiveFixedIncomeFund();
    FixedIncomeFund save(FixedIncomeFund fixedIncomeFund);
}
