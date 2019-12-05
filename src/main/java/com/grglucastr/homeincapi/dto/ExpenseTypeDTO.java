package com.grglucastr.homeincapi.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ExpenseTypeDTO implements Serializable {

    private Long id;
    private String name;
    private String category;


}
