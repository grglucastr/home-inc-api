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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ledger_registries")
public class LedgerRegistry extends BaseModel {

    private LocalDate billingDate;
    private LocalDate dueDate;
    private BigDecimal amountDue;
    private String barCode;
    private String QRCode;
    private Boolean paid = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(name = "payment_type_id")
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "spending_id")
    private Spending spending;

    @OneToMany(mappedBy = "ledgerRegistry")
    private List<FundDetail> fundDetails;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "se_ledger_registries")
    @SequenceGenerator(name = "se_ledger_registries", sequenceName = "se_ledger_registries", allocationSize = 1)
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
