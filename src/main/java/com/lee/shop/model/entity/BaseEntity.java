package com.lee.shop.model.entity;

import java.util.Objects;

public class BaseEntity<ID extends Number> {

    private ID id;

    protected BaseEntity() {
    }

    protected BaseEntity(ID id) {
        this.id = id;
    }

    public ID getId() {
        return this.id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity<?> that = (BaseEntity<?>) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
