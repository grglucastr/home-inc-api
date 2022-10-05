package com.grglucastr.homeincapi.controllers;

import com.grglucastr.homeincapi.api.SpendingsApi;
import com.grglucastr.homeincapi.models.BasicResponse;
import org.springframework.http.ResponseEntity;

public class SpendingController implements SpendingsApi {

    @Override
    public ResponseEntity<BasicResponse> getSpendings() {
        return SpendingsApi.super.getSpendings();
    }
}
