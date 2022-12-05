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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fund_details")
@Entity
public class FundDetail extends BaseModel{

    private BigDecimal lastYieldAmount;
    private BigDecimal dividendYield;
    private BigDecimal stockPrice;
    private Integer quantity;
    private BigDecimal pvp;

    @ManyToOne
    @JoinColumn(name = "ledger_registry_id")
    private LedgerRegistry ledgerRegistry;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "se_fund_details")
    @SequenceGenerator(name = "se_fund_details", sequenceName = "se_fund_details", allocationSize = 1)
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
