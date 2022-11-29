package com.grglucastr.homeincapi.mocks;

import com.grglucastr.homeincapi.models.LedgerRegistry;
import com.grglucastr.homeincapi.models.PaymentType;
import com.grglucastr.homeincapi.models.Spending;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class LedgerRegistryMocks {

    public static LedgerRegistry createSingleLedgerRegistry(){
        final LedgerRegistry ledgerRegistry = new LedgerRegistry();
        ledgerRegistry.setId(1L);
        ledgerRegistry.setInsertDateTime(MockLocalDate.getInsertDateTime());

        ledgerRegistry.setBillingDate(LocalDate.of(2025, 1, 1));
        ledgerRegistry.setDueDate(LocalDate.of(2025, 1,15));
        ledgerRegistry.setAmountDue(new BigDecimal("100.00"));
        ledgerRegistry.setBarCode("82670000000-1 90700109202-8 21015332000-2 13102022419-5");
        ledgerRegistry.setQRCode("lorem ipsum dolor sit amet");

        final Spending singleSpending = SpendingMocks.createSingleSpending();
        ledgerRegistry.setSpending(singleSpending);

        final PaymentType paymentType = PaymentTypeMocks.createSinglePaymentType();
        ledgerRegistry.setPaymentType(paymentType);

        return ledgerRegistry;
    }

    public static LedgerRegistry createSingleLedgerRegistry(Long id){
        final LedgerRegistry registry = createSingleLedgerRegistry();
        registry.setId(id);
        return registry;
    }

    public static List<LedgerRegistry> createListOfLedgerRegistries(){
        final LedgerRegistry registry1 = createSingleLedgerRegistry();

        final LedgerRegistry registry2 = createSingleLedgerRegistry(2L);
        registry2.setAmountDue(new BigDecimal("50.00"));
        final PaymentType p2 = PaymentTypeMocks.createSinglePaymentType(2L);
        p2.setName("Debit");
        registry2.setPaymentType(p2);

        final LedgerRegistry registry3 = createSingleLedgerRegistry(3L);
        registry3.setAmountDue(new BigDecimal("250.22"));
        final PaymentType p3 = PaymentTypeMocks.createSinglePaymentType(3L);
        p3.setName("Credit Card");
        registry3.setPaymentType(p3);
        registry3.setPaid(true);
        registry3.setUpdateDateTime(MockLocalDate.getUpdateDateTime());

        return Arrays.asList(registry1, registry2, registry3);
    }
}
