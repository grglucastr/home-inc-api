package com.grglucastr.homeincapi.controllers;

import com.grglucastr.homeincapi.configurations.ModelMapperConfiguration;
import com.grglucastr.homeincapi.mocks.FundDetailMocks;
import com.grglucastr.homeincapi.mocks.LedgerRegistryMocks;
import com.grglucastr.homeincapi.models.FundDetail;
import com.grglucastr.homeincapi.models.LedgerRegistry;
import com.grglucastr.homeincapi.services.FundDetailService;
import com.grglucastr.homeincapi.services.LedgerRegistryService;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {ModelMapperConfiguration.class, FundDetailsController.class})
public class FundDetailsControllerTest {

    private static final String BASE_URL = "/v3/ledger-registries/{ledgerRegistryId}/fund-details";
    private static final long LEDGER_REGISTRY_ID = 123L;
    public static final String FUND_DETAIL_REQUEST_PAYLOAD = "src/test/resources/fund-detail-request-payload.json";
    public static final String CHARSET_NAME = "UTF-8";

    @MockBean
    private LedgerRegistryService ledgerRegistryService;

    @MockBean
    private FundDetailService fundDetailService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        final LedgerRegistry singleLedgerRegistry = LedgerRegistryMocks.createSingleLedgerRegistry();
        when(ledgerRegistryService.findById(anyLong())).thenReturn(Optional.of(singleLedgerRegistry));
    }

    @Test
    void testListAllActiveFundDetails() throws Exception {

        final List<FundDetail> listOfActiveFundDetails = FundDetailMocks.createListOfActiveFundDetails();
        when(fundDetailService.listActiveFundDetailByLedgerRegistryId(anyLong()))
                .thenReturn(listOfActiveFundDetails);

        mockMvc.perform(get(BASE_URL, LEDGER_REGISTRY_ID)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$.[0].id", equalTo(1)))
                .andExpect(jsonPath("$.[0].active", is(true)))
                .andExpect(jsonPath("$.[0].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[0].updateDateTime", nullValue()))
                .andExpect(jsonPath("$.[0].lastYieldAmount", equalTo(1.35)))
                .andExpect(jsonPath("$.[0].dividendYield", equalTo(0.65)))
                .andExpect(jsonPath("$.[0].stockPrice", equalTo(189.57)))
                .andExpect(jsonPath("$.[0].quantity", equalTo(1)))
                .andExpect(jsonPath("$.[0].pvp", equalTo(0.87)))

                .andExpect(jsonPath("$.[1].id", equalTo(2)))
                .andExpect(jsonPath("$.[1].active", is(true)))
                .andExpect(jsonPath("$.[1].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[1].updateDateTime", nullValue()))
                .andExpect(jsonPath("$.[1].lastYieldAmount", equalTo(1.35)))
                .andExpect(jsonPath("$.[1].dividendYield", equalTo(0.65)))
                .andExpect(jsonPath("$.[1].stockPrice", equalTo(189.57)))
                .andExpect(jsonPath("$.[1].quantity", equalTo(1)))
                .andExpect(jsonPath("$.[1].pvp", equalTo(0.87)))

                .andExpect(jsonPath("$.[2].id", equalTo(3)))
                .andExpect(jsonPath("$.[2].active", is(true)))
                .andExpect(jsonPath("$.[2].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[2].updateDateTime", equalTo("2035-09-15T21:42:34")))
                .andExpect(jsonPath("$.[2].lastYieldAmount", equalTo(1.35)))
                .andExpect(jsonPath("$.[2].dividendYield", equalTo(0.65)))
                .andExpect(jsonPath("$.[2].stockPrice", equalTo(189.57)))
                .andExpect(jsonPath("$.[2].quantity", equalTo(1)))
                .andExpect(jsonPath("$.[2].pvp", equalTo(0.87)));
    }

    @Test
    void testPostNewFundDetail() throws Exception {

        final ArgumentCaptor<FundDetail> fundCaptor = ArgumentCaptor.forClass(FundDetail.class);
        final FundDetail singleFundDetail = FundDetailMocks.createSingleFundDetail();
        when(fundDetailService.save(fundCaptor.capture())).thenReturn(singleFundDetail);

        final String content = FileUtils.readFileToString(new File(FUND_DETAIL_REQUEST_PAYLOAD), CHARSET_NAME);

        mockMvc.perform(post(BASE_URL, LEDGER_REGISTRY_ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.active", is(true)))
                .andExpect(jsonPath("$.insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.updateDateTime", nullValue()))
                .andExpect(jsonPath("$.lastYieldAmount", equalTo(1.35)))
                .andExpect(jsonPath("$.dividendYield", equalTo(0.65)))
                .andExpect(jsonPath("$.stockPrice", equalTo(189.57)))
                .andExpect(jsonPath("$.quantity", equalTo(1)))
                .andExpect(jsonPath("$.pvp", equalTo(0.87)));

        final FundDetail fundDetail = fundCaptor.getValue();
        assertThat(fundDetail.getLastYieldAmount(), equalTo(new BigDecimal("1.1")));
        assertThat(fundDetail.getDividendYield(), equalTo(new BigDecimal("0.66")));
        assertThat(fundDetail.getStockPrice(), equalTo(new BigDecimal("169.39")));
        assertThat(fundDetail.getQuantity(), equalTo(1));
        assertThat(fundDetail.getPvp(), equalTo(new BigDecimal("1.15")));

    }
}
