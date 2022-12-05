package com.grglucastr.homeincapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "fixed_income_funds")
public class FixedIncomeFund extends Spending{

    private LocalDate dueDate;
    private BigDecimal annualProfitPercentage;
    private BigDecimal productPrice;
    private BigDecimal minAmountAllowed;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "se_fixed_income_funds")
    @SequenceGenerator(name = "se_fixed_income_funds", sequenceName = "se_fixed_income_funds", allocationSize = 1)
    @Override
    public Long getId() {
        return super.getId();
    }

    @Column(name = "active")
    @Override
    public Boolean getActive() {
        return super.getActive();
    }

    @Column(name = "insert_date_time")
    @Override
    public LocalDateTime getInsertDateTime() {
        return super.getInsertDateTime();
    }

    @Column(name = "update_date_time")
    @Override
    public LocalDateTime getUpdateDateTime() {
        return super.getUpdateDateTime();
    }
}
