package com.grglucastr.homeincapi.services.impl;

import com.grglucastr.homeincapi.models.FundDetail;
import com.grglucastr.homeincapi.repositories.FundDetailRepository;
import com.grglucastr.homeincapi.services.FundDetailService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FundDetailServiceImpl implements FundDetailService {

    @Autowired
    private FundDetailRepository repository;

    @Override
    public List<FundDetail> listActiveFundDetailByLedgerRegistryId(Long ledgerRegistryId) {
        return repository.findAllByLedgerRegistryIdAndActiveTrue(ledgerRegistryId);
    }

    @Override
    public FundDetail save(FundDetail fundDetail) {
        return repository.save(fundDetail);
    }
}
