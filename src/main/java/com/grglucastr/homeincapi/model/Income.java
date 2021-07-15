package com.grglucastr.homeincapi.model;

import com.grglucastr.homeincapi.enums.Periodicity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@Entity(name = "income")
@Table(name = "income")
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "se_income")
    @SequenceGenerator(name = "se_income", sequenceName = "se_income", allocationSize = 1)
    private Long id;

    @NotNull
    private BigDecimal amount;

    @Length(min = 3, max = 100)
    private String description;

    @Enumerated(value = EnumType.STRING)
    private Periodicity type;

    @Column(name = "active")
    private boolean active = Boolean.TRUE;

    @Column(name = "accounting_period_start")
    private LocalDate accountingPeriodStart;

    @Column(name = "accounting_period_end")
    private LocalDate accountingPeriodEnd;

    @CreatedDate
    private LocalDate insertDate;

    private LocalDate updateDate;
}
