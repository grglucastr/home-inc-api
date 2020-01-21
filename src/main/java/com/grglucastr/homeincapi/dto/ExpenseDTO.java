package com.grglucastr.homeincapi.dto;

import com.grglucastr.homeincapi.model.Periodicity;
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
    private Periodicity periodicity;
    private String paymentMethod;
    private LocalDate paidDate;
    private LocalDate servicePeriodStart;
    private LocalDate servicePeriodEnd;
    private LocalDate insertDate;
    private LocalDate updateDate;
    private ExpenseTypeDTO expenseType;

    public ExpenseDTO() {
    }

    public ExpenseDTO(String title, String description, BigDecimal cost, LocalDate dueDate) {
        this();
        this.title = title;
        this.description = description;
        this.cost = cost;
        this.dueDate = dueDate;
    }

    public ExpenseDTO(Long id, String title, String description, BigDecimal cost, LocalDate dueDate) {
        this(title, description, cost, dueDate);
        this.id = id;
    }
}
