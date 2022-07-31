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

    private static final String ACTION_EMAIL_IS_REQUIRED_TEMPLATE = "action.email.is.required";
    private static final String ACTION_EMAIL_HAS_INVALID_FORMAT_TEMPLATE = "action.email.has.invalid.format";
    private static final String ACTION_PASSWORD_IS_REQUIRED_TEMPLATE = "action.password.is.required";

    @Override
    public void validate(SignInDto signInDto) {
        Map<String, String> errorsMap = new HashMap<>();
        validateEmailIsRequired(signInDto, errorsMap);
        validatePasswordIsRequired(signInDto, errorsMap);
        if (!errorsMap.isEmpty()) {
            throw new InvalidUserInputException(SIGN_IN_JSP, errorsMap);
        }
    }

    private void validateEmailIsRequired(SignInDto signInDto, Map<String, String> errorsMap) {
        if (signInDto.getEmail() == null || signInDto.getEmail().trim().isEmpty()) {
            errorsMap.put(EMAIL, ACTION_EMAIL_IS_REQUIRED_TEMPLATE);
        } else {
            if (!Constants.EMAIL_PATTERN.matcher(signInDto.getEmail()).matches()) {
                errorsMap.put(EMAIL, ACTION_EMAIL_HAS_INVALID_FORMAT_TEMPLATE);
            }
        }
    }

    private void validatePasswordIsRequired(SignInDto signInDto, Map<String, String> errorsMap) {
        if (signInDto.getPassword() == null || signInDto.getPassword().trim().isEmpty()) {
            errorsMap.put(PASSWORD, ACTION_PASSWORD_IS_REQUIRED_TEMPLATE);
        }
    }
}
