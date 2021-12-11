package com.grglucastr.homeincapi.enums;

import lombok.Getter;

@Getter
public enum PaymentMethod {

    CASH("Cash", "cash"),
    DEBIT_CARD("Debit", "debit card"),
    CREDIT_CARD("Credit Card", "credit card"),
    TICKET("Ticket", "ticket"),
    BANK_TRANSFER("Bank Transfer", "bank transfer"),
    PIX("pix", "pix");

    private String value;
    private String patchValue;

    PaymentMethod(String value, String patchValue) {
        this.value = value;
        this.patchValue = patchValue;
    }

    public String getPatchValue() {
        return patchValue;
    }

    @Override
    public String toString() {
        return value;
    }
}
