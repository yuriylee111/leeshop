package com.lee.shop.validator;

import java.util.Map;

public interface LogicValidator<D, E> {

    Map<String, String> getErrors(D dto, E entity);
}
