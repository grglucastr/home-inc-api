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
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "spendings")
public class Spending extends BaseModel{

    private String name;
    private Integer installments;
    private String description;

    @ManyToOne
    @JoinColumn(name = "spending_category_id")
    private SpendingCategory spendingCategory;

    @OneToMany(mappedBy = "spending")
    private List<LedgerRegistry> ledgerRegistries;


    public Spending(String name, Integer installments, String description, SpendingCategory spendingCategory) {
        this.name = name;
        this.installments = installments;
        this.description = description;
        this.spendingCategory = spendingCategory;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "se_spendings")
    @SequenceGenerator(name = "se_spendings", sequenceName = "se_spendings")
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
