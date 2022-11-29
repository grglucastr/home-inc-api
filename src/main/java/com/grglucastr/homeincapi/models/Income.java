package com.grglucastr.homeincapi.models;

import com.grglucastr.homeincapi.enums.Periodicity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "incomes")
@Entity
public class Income extends BaseModel {

    private String name;
    private String currencyCode;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Periodicity periodicity;

    @ManyToOne
    @JoinColumn(name = "income_category_id")
    private IncomeCategory incomeCategory;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "se_incomes")
    @SequenceGenerator(name = "se_incomes", sequenceName = "se_incomes")
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
