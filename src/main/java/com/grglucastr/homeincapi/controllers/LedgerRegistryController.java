package com.grglucastr.homeincapi.controllers;

import com.grglucastr.homeincapi.api.LedgerRegistriesApi;
import com.grglucastr.homeincapi.models.LedgerRegistry;
import com.grglucastr.homeincapi.models.LedgerRegistryRequest;
import com.grglucastr.homeincapi.models.LedgerRegistryResponse;
import com.grglucastr.homeincapi.models.PaymentType;
import com.grglucastr.homeincapi.models.Spending;
import com.grglucastr.homeincapi.services.LedgerRegistryService;
import com.grglucastr.homeincapi.services.PaymentTypeService;
import com.grglucastr.homeincapi.services.SpendingService;
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
public class LedgerRegistryController implements LedgerRegistriesApi {

    @Autowired
    private SpendingService spendingService;

    @Autowired
    private LedgerRegistryService registryService;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<List<LedgerRegistryResponse>> getLedgerRegistriesBySpendingId(Long spendingId) {

        final Optional<Spending> optSpending = spendingService.findById(spendingId);
        if(optSpending.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        final List<LedgerRegistry> ledgerRegistries = registryService
                .listActiveLedgerRegistriesBySpendingId(spendingId);

        final List<LedgerRegistryResponse> response = ledgerRegistries
                .stream()
                .map(lr -> modelMapper.map(lr, LedgerRegistryResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<LedgerRegistryResponse> postNewRegistryToLedger(Long spendingId, LedgerRegistryRequest ledgerRegistryRequest) {

        final Optional<Spending> optSpending = spendingService.findById(spendingId);
        if(optSpending.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        final Optional<PaymentType> optPaymentType = paymentTypeService.findById(ledgerRegistryRequest.getPaymentTypeId());
        if(optPaymentType.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        final LedgerRegistry newLedgerRegistry = modelMapper.map(ledgerRegistryRequest, LedgerRegistry.class);
        newLedgerRegistry.setSpending(optSpending.get());
        newLedgerRegistry.setPaymentType(optPaymentType.get());

        final LedgerRegistry savedRegistry = registryService.save(newLedgerRegistry);
        final LedgerRegistryResponse response = modelMapper.map(savedRegistry, LedgerRegistryResponse.class);

        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
