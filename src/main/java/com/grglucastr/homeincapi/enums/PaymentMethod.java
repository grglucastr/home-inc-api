package com.grglucastr.homeincapi.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {

    BANK_TRANSFER("Bank Transfer"),
    CASH("Cash"),
    CREDIT_CARD("Credit Card"),
    DEBIT_CARD("Debit"),
    PIX("pix");

    private String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
