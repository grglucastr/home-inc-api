package com.grglucastr.homeincapi.controller;

import com.grglucastr.homeincapi.dto.PaymentMethodDTO;
import com.grglucastr.homeincapi.enums.PaymentMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

    @GetMapping
    public List<PaymentMethodDTO> listAll(){
        return Arrays.stream(PaymentMethod.values())
                .map(v -> new PaymentMethodDTO(v.getValue(), v))
                .collect(Collectors.toList());
    }
}
