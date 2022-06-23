package com.lee.shop.validator;

import java.util.Map;

public interface LogicValidator<F, E> {

    Map<String, String> getErrors(F form, E entity);
}
