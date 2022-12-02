package com.grglucastr.homeincapi.controllers;

import com.grglucastr.homeincapi.api.FundDetailsApi;
import com.grglucastr.homeincapi.models.FundDetail;
import com.grglucastr.homeincapi.models.FundDetailRequest;
import com.grglucastr.homeincapi.models.FundDetailResponse;
import com.grglucastr.homeincapi.models.LedgerRegistry;
import com.grglucastr.homeincapi.services.FundDetailService;
import com.grglucastr.homeincapi.services.LedgerRegistryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class FundDetailsController implements FundDetailsApi {

    @Autowired
    private FundDetailService fundDetailService;

    @Autowired
    private LedgerRegistryService ledgerRegistryService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<FundDetailResponse> postFundDetail(Long ledgerRegistryId, FundDetailRequest fundDetailRequest) {

        final Optional<LedgerRegistry> optLedgerRegistry = ledgerRegistryService.findById(ledgerRegistryId);
        if(optLedgerRegistry.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        final FundDetail newFundDetail = modelMapper.map(fundDetailRequest, FundDetail.class);
        newFundDetail.setLedgerRegistry(optLedgerRegistry.get());
        final FundDetail savedFundDetail = fundDetailService.save(newFundDetail);
        final FundDetailResponse response = modelMapper.map(savedFundDetail, FundDetailResponse.class);

        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @Override
    public ResponseEntity<List<FundDetailResponse>> getFundDetails(Long ledgerRegistryId) {

        final Optional<LedgerRegistry> optLedgerRegistry = ledgerRegistryService.findById(ledgerRegistryId);
        if(optLedgerRegistry.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        final List<FundDetail> fundDetails = fundDetailService.listActiveFundDetailByLedgerRegistryId(ledgerRegistryId);
        final List<FundDetailResponse> response = fundDetails
                .stream()
                .map(fd -> modelMapper.map(fd, FundDetailResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
