package com.grglucastr.homeincapi.services;

import com.grglucastr.homeincapi.models.FundDetail;

import java.util.List;

public interface FundDetailService {
    List<FundDetail> listActiveIncomeCategories(Long userId);
    FundDetail save(FundDetail fundDetail);
}