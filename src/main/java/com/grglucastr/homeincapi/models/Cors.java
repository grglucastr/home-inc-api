package com.grglucastr.homeincapi.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cors")
@Entity
public class Cors extends BaseModel{

    private String allowedOrigin;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "se_cors")
    @SequenceGenerator(sequenceName = "se_cors", name = "se_cors", allocationSize = 1)
    @Override
    public Long getId() {
        return super.getId();
    }

    @Column(name = "active")
    @Override
    public Boolean getActive() {
        return super.getActive();
    }

    @Override
    public LocalDateTime getInsertDateTime() {
        return super.getInsertDateTime();
    }

    @Override
    public LocalDateTime getUpdateDateTime() {
        return super.getUpdateDateTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cors cors = (Cors) o;
        return Objects.equals(getAllowedOrigin(), cors.getAllowedOrigin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAllowedOrigin());
    }
}
