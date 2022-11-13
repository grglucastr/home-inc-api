package com.grglucastr.homeincapi.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public abstract class BaseModel {

    private Long id;
    private Boolean active = Boolean.TRUE;
    private LocalDateTime insertDateTime;
    private LocalDateTime updateDateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseModel baseModel = (BaseModel) o;
        return Objects.equals(getId(), baseModel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
