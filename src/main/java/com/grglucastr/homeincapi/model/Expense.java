package com.grglucastr.homeincapi.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Entity(name = "expense")
@Table(name = "expense")
@Data
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min=3, max = 80)
    private String title;

    @Length(min = 0, max = 255)
    private String description;

    @NotNull
    private BigDecimal cost;

    @NotNull
    private LocalDate dueDate;

    @NotNull
    private Boolean paid = false;

    private LocalDate invoiceDate;
    private LocalDate servicePeriodStart;
    private LocalDate servicePeriodEnd;

    @Enumerated(value = EnumType.STRING)
    private Periodicity periodicity;

    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "expense_type_id")
    private ExpenseType expenseType;


}
