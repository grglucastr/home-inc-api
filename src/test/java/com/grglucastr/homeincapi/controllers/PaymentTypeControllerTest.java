package com.grglucastr.homeincapi.controllers;

import com.grglucastr.homeincapi.configurations.ModelMapperConfiguration;
import com.grglucastr.homeincapi.mocks.PaymentTypeMocks;
import com.grglucastr.homeincapi.mocks.UserMocks;
import com.grglucastr.homeincapi.models.PaymentType;
import com.grglucastr.homeincapi.models.User;
import com.grglucastr.homeincapi.services.PaymentTypeService;
import com.grglucastr.homeincapi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {ModelMapperConfiguration.class, PaymentTypeController.class})
@ActiveProfiles("local")
public class PaymentTypeControllerTest {

    private static final String BASE_URL = "/v3/users/{userId}/payment-types";
    private static final Long USER_ID = 1234L;

    @MockBean
    private UserService userService;

    @MockBean
    private PaymentTypeService paymentTypeService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        final User singleUser = UserMocks.getSingleUser();
        when(userService.findById(anyLong())).thenReturn(Optional.of(singleUser));
    }

    @Test
    void testListAllActivePaymentTypeByUser() throws Exception {

        final List<PaymentType> activePaymentTypes = PaymentTypeMocks.createListOfActivePaymentType();
        when(paymentTypeService.listActivePaymentTypes(anyLong())).thenReturn(activePaymentTypes);

        mockMvc.perform(get(BASE_URL, USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].active", is(true)))
                .andExpect(jsonPath("$.[0].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[0].updateDateTime", nullValue()))
                .andExpect(jsonPath("$.[0].name", equalTo("Cash")))
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[1].active", is(true)))
                .andExpect(jsonPath("$.[1].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[1].updateDateTime", nullValue()))
                .andExpect(jsonPath("$.[1].name", equalTo("Debit")))
                .andExpect(jsonPath("$.[2].id", is(3)))
                .andExpect(jsonPath("$.[2].active", is(true)))
                .andExpect(jsonPath("$.[2].insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.[2].updateDateTime", equalTo("2035-09-15T21:42:34")))
                .andExpect(jsonPath("$.[2].name", equalTo("Credit Card")));
    }

    @Test
    void testPostNewPaymentType() throws Exception{

        final MockHttpServletRequestBuilder post = post(BASE_URL, USER_ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Cash1\"}");

        final ArgumentCaptor<PaymentType> captorPaymentType = ArgumentCaptor.forClass(PaymentType.class);

        final PaymentType singlePaymentType = PaymentTypeMocks.createSinglePaymentType();
        when(paymentTypeService.save(captorPaymentType.capture())).thenReturn(singlePaymentType);

        mockMvc.perform(post)
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.active", is(true)))
                .andExpect(jsonPath("$.insertDateTime", equalTo("2035-09-10T14:21:34")))
                .andExpect(jsonPath("$.updateDateTime", nullValue()))
                .andExpect(jsonPath("$.name", equalTo("Cash")));

        final PaymentType paymentType = captorPaymentType.getValue();
        assertThat(paymentType, notNullValue());
        assertThat(paymentType.getName(), equalTo("Cash1"));
    }
}
