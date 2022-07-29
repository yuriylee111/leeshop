package com.lee.shop.validator.dto;

import com.lee.shop.Constants;
import com.lee.shop.exception.InvalidUserInputException;
import com.lee.shop.model.dto.SignInDto;
import com.lee.shop.validator.DtoValidator;

import java.util.HashMap;
import java.util.Map;

public class SignInDtoValidator implements DtoValidator<SignInDto> {

    private static final String SIGN_IN_JSP = "sign-in.jsp";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    @Override
    public void validate(SignInDto signInDto) {
        Map<String, String> errorsMap = new HashMap<>();
        if (signInDto.getEmail() == null || signInDto.getEmail().trim().isEmpty()) {
            errorsMap.put(EMAIL, "action.email.is.required");
        } else {
            if (!Constants.EMAIL_PATTERN.matcher(signInDto.getEmail()).matches()) {
                errorsMap.put(EMAIL, "action.email.has.invalid.format");
            }
        }
        if (signInDto.getPassword() == null || signInDto.getPassword().trim().isEmpty()) {
            errorsMap.put(PASSWORD, "action.password.is.required");
        }
        if (!errorsMap.isEmpty()) {
            throw new InvalidUserInputException(SIGN_IN_JSP, errorsMap);
        }
    }
}
