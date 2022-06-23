package com.lee.shop.validator;

import java.util.Map;

public interface FormValidator<F> {

    Map<String, String> getErrors(F form);
}
