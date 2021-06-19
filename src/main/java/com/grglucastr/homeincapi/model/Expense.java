package com.grglucastr.homeincapi.model;

import com.grglucastr.homeincapi.enums.PaymentMethod;
import com.grglucastr.homeincapi.enums.Periodicity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@Entity(name = "expense")
@Table(name = "expense")
@ToString
public class Expense implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "se_expense")
    @SequenceGenerator(name = "se_expense", sequenceName = "se_expense", allocationSize = 1)
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
    private boolean paid = Boolean.FALSE;

    private LocalDate invoiceDate;
    private LocalDate servicePeriodStart;
    private LocalDate servicePeriodEnd;

    @Enumerated(value = EnumType.STRING)
    private Periodicity periodicity;

    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @NotNull
    private Boolean isActive = Boolean.TRUE;

    private LocalDate paidDate;

    @CreationTimestamp
    private OffsetDateTime insertDateTime;

    @UpdateTimestamp
    private OffsetDateTime updateDateTime;

    public Expense() {
    }

    public Expense(Long id, @Length(min = 3, max = 80) String title) {
        this();
        this.id = id;
        this.title = title;
    }

    public Expense(Long id, @Length(min = 3, max = 80) String title, @Length(min = 0, max = 255) String description, @NotNull BigDecimal cost, @NotNull LocalDate dueDate) {
        this(id, title);
        this.description = description;
        this.cost = cost;
        this.dueDate = dueDate;
    }

    public void setPaid(Boolean paid) {
        if(paid){
            this.setPaidDate(LocalDate.now());
        }
        this.paid = paid;
    }

    public void setPaidDate(LocalDate paidDate) {
        this.paidDate = paidDate;
    }
}
