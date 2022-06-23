package com.lee.shop.validator.form;

import com.lee.shop.Constants;
import com.lee.shop.model.form.SignInForm;
import com.lee.shop.validator.FormValidator;

import java.util.HashMap;
import java.util.Map;

public class SignInFormValidator implements FormValidator<SignInForm> {

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    @Override
    public Map<String, String> getErrors(SignInForm signInForm) {
        Map<String, String> map = new HashMap<>();
        if (signInForm.getEmail() == null || signInForm.getEmail().trim().isEmpty()) {
            map.put(EMAIL, "action.email.is.required");
        } else {
            if (!Constants.EMAIL_PATTERN.matcher(signInForm.getEmail()).matches()) {
                map.put(EMAIL, "action.email.has.invalid.format");
            }
        }
        if (signInForm.getPassword() == null || signInForm.getPassword().trim().isEmpty()) {
            map.put(PASSWORD, "action.password.is.required");
        }
        return map;
    }
}
