package com.grglucastr.homeincapi.dto;

import com.grglucastr.homeincapi.enums.Periodicity;
import lombok.Getter;

@Getter
public class PeriodicityDTO {

    private String label;
    private Periodicity value;


    public PeriodicityDTO(String label, Periodicity value) {
        this.label = label;
        this.value = value;
    }
}
