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
    private boolean paid;
    private LocalDate dueDate;
    private LocalDate invoiceDate;
    private String periodicity;
    private String paymentMethod;
    private LocalDate paidDate;
    private LocalDate servicePeriodStart;
    private LocalDate servicePeriodEnd;
    private LocalDate insertDate;
    private LocalDate updateDate;
    private ExpenseTypeDTO expenseType;

    public String getServicePeriodStart() {
        if(servicePeriodStart == null){
            return "";
        }
        return servicePeriodStart.toString();
    }

    public String getServicePeriodEnd() {
        if(servicePeriodEnd == null){
            return "";
        }
        return servicePeriodEnd.toString();
    }
}
