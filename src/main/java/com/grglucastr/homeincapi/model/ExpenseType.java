package com.grglucastr.homeincapi.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Entity(name = "expense_type")
@Table(name = "expense_type")
public class ExpenseType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "se_expense_type")
    @SequenceGenerator(name = "se_expense_type", sequenceName = "se_expense_type", allocationSize = 1)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private ExpenseTypeCategory category;

    @OneToMany(mappedBy = "expenseType")
    private Set<Expense> expenses;
}
