package com.lee.shop.validator.dto;

import com.lee.shop.Constants;
import com.lee.shop.model.dto.SignInDto;
import com.lee.shop.validator.DtoValidator;

import java.util.HashMap;
import java.util.Map;

public class SignInDtoValidator implements DtoValidator<SignInDto> {

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    @Override
    public Map<String, String> getErrors(SignInDto signInDto) {
        Map<String, String> map = new HashMap<>();
        if (signInDto.getEmail() == null || signInDto.getEmail().trim().isEmpty()) {
            map.put(EMAIL, "action.email.is.required");
        } else {
            if (!Constants.EMAIL_PATTERN.matcher(signInDto.getEmail()).matches()) {
                map.put(EMAIL, "action.email.has.invalid.format");
            }
        }
        if (signInDto.getPassword() == null || signInDto.getPassword().trim().isEmpty()) {
            map.put(PASSWORD, "action.password.is.required");
        }
        return map;
    }
}
