package com.grglucastr.homeincapi.controllers;

import com.grglucastr.homeincapi.api.PaymentTypesApi;
import com.grglucastr.homeincapi.models.PaymentType;
import com.grglucastr.homeincapi.models.PaymentTypeRequest;
import com.grglucastr.homeincapi.models.PaymentTypeResponse;
import com.grglucastr.homeincapi.models.User;
import com.grglucastr.homeincapi.services.PaymentTypeService;
import com.grglucastr.homeincapi.services.UserService;
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
public class PaymentTypeController implements PaymentTypesApi {

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<List<PaymentTypeResponse>> getPaymentTypes(Long userId) {

        final Optional<User> opUser = userService.findById(userId);
        if(opUser.isEmpty())
            return ResponseEntity.notFound().build();

        final List<PaymentType> paymentTypes = paymentTypeService.listActivePaymentTypes(userId);
        final List<PaymentTypeResponse> response = paymentTypes.stream()
                .map(p -> modelMapper.map(p, PaymentTypeResponse.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PaymentTypeResponse> postPaymentType(Long userId, PaymentTypeRequest paymentTypeRequest) {

        final Optional<User> opUser = userService.findById(userId);
        if(opUser.isEmpty())
            return ResponseEntity.notFound().build();

        final PaymentType paymentType = modelMapper.map(paymentTypeRequest, PaymentType.class);
        final PaymentType paymentTypeSaved = paymentTypeService.save(paymentType);
        final PaymentTypeResponse response = modelMapper.map(paymentTypeSaved, PaymentTypeResponse.class);

        final URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
