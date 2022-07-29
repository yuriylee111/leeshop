package com.lee.shop.validator;

public interface LogicValidator<D, E> {

    void validate(D dto, E entity);
}
