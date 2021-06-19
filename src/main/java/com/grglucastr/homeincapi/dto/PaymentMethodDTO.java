package com.grglucastr.homeincapi.dto;

import com.grglucastr.homeincapi.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodDTO {
    private String label;
    private PaymentMethod value;
}
