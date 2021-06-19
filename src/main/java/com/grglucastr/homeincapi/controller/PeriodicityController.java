package com.grglucastr.homeincapi.controller;

import com.grglucastr.homeincapi.dto.PeriodicityDTO;
import com.grglucastr.homeincapi.enums.Periodicity;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/periodicities")
@Slf4j
public class PeriodicityController {

    private ModelMapper mapper;

    @Autowired
    public PeriodicityController(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @GetMapping
    public List<PeriodicityDTO> listAll(){
        return Arrays.stream(Periodicity.values())
                .map(v -> new PeriodicityDTO(v.getValue(), v))
                .collect(Collectors.toList());


    }
}
