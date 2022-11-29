package com.grglucastr.homeincapi.controllers;

import com.grglucastr.homeincapi.configurations.ModelMapperConfiguration;
import com.grglucastr.homeincapi.mocks.LedgerRegistryMocks;
import com.grglucastr.homeincapi.mocks.PaymentTypeMocks;
import com.grglucastr.homeincapi.mocks.SpendingMocks;
import com.grglucastr.homeincapi.models.LedgerRegistry;
import com.grglucastr.homeincapi.models.PaymentType;
import com.grglucastr.homeincapi.models.Spending;
import com.grglucastr.homeincapi.services.LedgerRegistryService;
import com.grglucastr.homeincapi.services.PaymentTypeService;
import com.grglucastr.homeincapi.services.SpendingService;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {ModelMapperConfiguration.class, LedgerRegistryController.class})
public class LedgerRegistryControllerTest {

    private static final String BASE_URL = "/v3/spendings/{spendingId}/ledger-registries";
    private static final long SPENDING_ID = 123L;
    public static final String CHARSET_NAME = "UTF-8";
    public static final String REGISTRY_PAYLOAD_REQUEST_JSON = "src/test/resources/ledger-registry-request-payload.json";

    @MockBean
    private LedgerRegistryService registryService;

    @MockBean
    private SpendingService spendingService;

    @MockBean
    private PaymentTypeService paymentTypeService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        final Spending singleSpending = SpendingMocks.createSingleSpending();
        when(spendingService.findById(anyLong())).thenReturn(Optional.of(singleSpending));
    }

    @Test
    void testListAllLedgersBySpendingId() throws Exception{

        final List<LedgerRegistry> listOfLedgerRegistries = LedgerRegistryMocks.createListOfLedgerRegistries();

        when(registryService.listActiveLedgerRegistriesBySpendingId(anyLong()))
                .thenReturn(listOfLedgerRegistries);

        mockMvc.perform(get(BASE_URL, SPENDING_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$.[0].id", equalTo(1)))
                .andExpect(jsonPath("$.[0].active", is(true)))
                .andExpect(jsonPath("$.[0].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[0].updateDateTime", nullValue()))
                .andExpect(jsonPath("$.[0].paymentType", equalTo("Cash")))
                .andExpect(jsonPath("$.[0].billingDate", equalTo("2025-01-01")))
                .andExpect(jsonPath("$.[0].dueDate", equalTo("2025-01-15")))
                .andExpect(jsonPath("$.[0].amountDue", equalTo(100.0)))
                .andExpect(jsonPath("$.[0].barCode", equalTo("82670000000-1 90700109202-8 21015332000-2 13102022419-5")))
                .andExpect(jsonPath("$.[0].QRCode", equalTo("lorem ipsum dolor sit amet")))
                .andExpect(jsonPath("$.[0].paid", is(false)))

                .andExpect(jsonPath("$.[1].id", equalTo(2)))
                .andExpect(jsonPath("$.[1].active", is(true)))
                .andExpect(jsonPath("$.[1].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[1].updateDateTime", nullValue()))
                .andExpect(jsonPath("$.[1].paymentType", equalTo("Debit")))
                .andExpect(jsonPath("$.[1].billingDate", equalTo("2025-01-01")))
                .andExpect(jsonPath("$.[1].dueDate", equalTo("2025-01-15")))
                .andExpect(jsonPath("$.[1].amountDue", equalTo(50.0)))
                .andExpect(jsonPath("$.[1].barCode", equalTo("82670000000-1 90700109202-8 21015332000-2 13102022419-5")))
                .andExpect(jsonPath("$.[1].QRCode", equalTo("lorem ipsum dolor sit amet")))
                .andExpect(jsonPath("$.[1].paid", is(false)))

                .andExpect(jsonPath("$.[2].id", equalTo(3)))
                .andExpect(jsonPath("$.[2].active", is(true)))
                .andExpect(jsonPath("$.[2].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[2].updateDateTime", equalTo("2035-09-15T21:42:34")))
                .andExpect(jsonPath("$.[2].paymentType", equalTo("Credit Card")))
                .andExpect(jsonPath("$.[2].billingDate", equalTo("2025-01-01")))
                .andExpect(jsonPath("$.[2].dueDate", equalTo("2025-01-15")))
                .andExpect(jsonPath("$.[2].amountDue", equalTo(250.22)))
                .andExpect(jsonPath("$.[2].barCode", equalTo("82670000000-1 90700109202-8 21015332000-2 13102022419-5")))
                .andExpect(jsonPath("$.[2].QRCode", equalTo("lorem ipsum dolor sit amet")))
                .andExpect(jsonPath("$.[2].paid", is(true)));
    }


    @Test
    void testPostNewRegistryToTheLedger() throws Exception {

        final ArgumentCaptor<LedgerRegistry> registryCaptor = ArgumentCaptor.forClass(LedgerRegistry.class);

        final LedgerRegistry singleLedgerRegistry = LedgerRegistryMocks.createSingleLedgerRegistry();
        when(registryService.save(registryCaptor.capture())).thenReturn(singleLedgerRegistry);

        final PaymentType singlePaymentType = PaymentTypeMocks.createSinglePaymentType();
        when(paymentTypeService.findById(anyLong())).thenReturn(Optional.of(singlePaymentType));

        final String content = FileUtils.readFileToString(new File(REGISTRY_PAYLOAD_REQUEST_JSON), CHARSET_NAME);
        mockMvc.perform(post(BASE_URL, SPENDING_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.active", is(true)))
                .andExpect(jsonPath("$.insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.updateDateTime", nullValue()))
                .andExpect(jsonPath("$.paymentType", equalTo("Cash")))
                .andExpect(jsonPath("$.billingDate", equalTo("2025-01-01")))
                .andExpect(jsonPath("$.dueDate", equalTo("2025-01-15")))
                .andExpect(jsonPath("$.amountDue", equalTo(100.0)))
                .andExpect(jsonPath("$.barCode", equalTo("82670000000-1 90700109202-8 21015332000-2 13102022419-5")))
                .andExpect(jsonPath("$.QRCode", equalTo("lorem ipsum dolor sit amet")))
                .andExpect(jsonPath("$.paid", is(false)));

        final LedgerRegistry registry = registryCaptor.getValue();
        assertThat(registry, notNullValue());
        assertThat(registry.getPaymentType(), notNullValue());
        assertThat(registry.getPaymentType().getId(), equalTo(1L));
        assertThat(registry.getBillingDate(), equalTo(LocalDate.of(2033,10,11)));
        assertThat(registry.getDueDate(), equalTo(LocalDate.of(2033,10,15)));
        assertThat(registry.getAmountDue(), equalTo(new BigDecimal("1234.22")));
        assertThat(registry.getBarCode(), equalTo("836800000009 672801110004 001010202230 533649207040"));
        assertThat(registry.getQRCode(), emptyString());
        assertThat(registry.getPaid(), is(false));
    }
}