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
@Table(name = "income_categories")
public class IncomeCategory extends BaseModel {

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "incomeCategory")
    private List<Income> incomes;

    public IncomeCategory(String name, User user) {
        this.name = name;
        this.user = user;
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

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "se_income_category")
    @SequenceGenerator(name = "se_income_category", sequenceName = "se_income_category", allocationSize = 1)
    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
