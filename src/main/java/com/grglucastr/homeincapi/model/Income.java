package com.grglucastr.homeincapi.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@Entity(name = "income")
@Table(name = "income")
@ToString
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "se_income")
    @SequenceGenerator(name = "se_income", sequenceName = "se_income", allocationSize = 1)
    private Long id;

    @NotNull
    private BigDecimal amount;

    @Length(min = 3, max = 100)
    private String description;
}
