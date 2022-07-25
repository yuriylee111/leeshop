package com.lee.shop.validator;

import java.util.Map;

public interface DtoValidator<D> {

    Map<String, String> getErrors(D dto);
}
