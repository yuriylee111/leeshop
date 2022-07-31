package com.lee.shop.validator.logic;

import com.lee.shop.exception.InvalidUserInputException;
import com.lee.shop.model.dto.SignInDto;
import com.lee.shop.model.entity.User;
import com.lee.shop.security.PasswordEncoder;
import com.lee.shop.validator.LogicValidator;
import org.apache.log4j.Logger;

import java.util.Collections;

public class SignInLogicValidator implements LogicValidator<SignInDto, User> {

    private static final Logger LOGGER = Logger.getLogger(SignInLogicValidator.class);

    private static final String SIGN_IN_JSP = "sign-in.jsp";
    private static final String EMAIL = "email";

    private static final String ACTION_EMAIL_SIGN_IN_FAILED_TEMPLATE = "action.email.sign-in.failed";
    private static final String ACTION_EMAIL_BLOCKED_TEMPLATE = "action.email.blocked";
    private static final String USER_NOT_FOUND_BY_EMAIL_TEMPLATE = "User not found by email: %s";
    private static final String USER_IS_BLOCKED_FOR_EMAIL_TEMPLATE = "User is blocked for email: %s";
    private static final String PASSWORD_NOT_VALID_FOR_TEMPLATE = "Password not valid for %s";

    private final PasswordEncoder passwordEncoder;

    public SignInLogicValidator(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void validate(SignInDto signInDto, User user) {
        validateUserIsFoundByEmail(signInDto, user);
        validateUserIsActive(signInDto,user);
        validatePasswordIsValid(signInDto, user);
    }

    private void validateUserIsFoundByEmail(SignInDto signInDto, User user) {
        if (user == null) {
            LOGGER.warn(String.format(USER_NOT_FOUND_BY_EMAIL_TEMPLATE, signInDto.getEmail()));
            throw new InvalidUserInputException(
                    SIGN_IN_JSP, Collections.singletonMap(EMAIL, ACTION_EMAIL_SIGN_IN_FAILED_TEMPLATE));
        }
    }

    private void validateUserIsActive(SignInDto signInDto, User user) {
        if (!user.isActive()) {
            LOGGER.warn(String.format(USER_IS_BLOCKED_FOR_EMAIL_TEMPLATE, signInDto.getEmail()));
            throw new InvalidUserInputException(
                    SIGN_IN_JSP, Collections.singletonMap(EMAIL, ACTION_EMAIL_BLOCKED_TEMPLATE));
        }
    }

    private void validatePasswordIsValid(SignInDto signInDto, User user) {
        if (!passwordEncoder.match(signInDto.getPassword(), user.getPassword())) {
            LOGGER.warn(String.format(PASSWORD_NOT_VALID_FOR_TEMPLATE, signInDto.getEmail()));
            throw new InvalidUserInputException(
                    SIGN_IN_JSP, Collections.singletonMap(EMAIL, ACTION_EMAIL_SIGN_IN_FAILED_TEMPLATE));
        }
    }
}
