package com.grglucastr.homeincapi.controllers;

import com.grglucastr.homeincapi.api.SpendingsApi;
import com.grglucastr.homeincapi.models.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController("SpendingController")
public class SpendingController implements SpendingsApi {

    @Override
    public ResponseEntity<BasicResponse> getSpendings() {
        return ResponseEntity.ok(new BasicResponse());
    }
}
