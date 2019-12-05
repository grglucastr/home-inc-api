package com.grglucastr.homeincapi.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ExpenseDTO implements Serializable {

    private Long id;
    private Boolean isActive;
    private String title;
    private String description;
    private BigDecimal cost;
    private Boolean paid;
    private LocalDate dueDate;
    private LocalDate invoiceDate;
    private String periodicity;
    private String paymentMethod;
    private ExpenseTypeDTO expenseType;


}
