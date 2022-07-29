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
            LOGGER.warn("User not found by email: " + signInDto.getEmail());
            throw new InvalidUserInputException(
                    SIGN_IN_JSP, Collections.singletonMap(EMAIL, "action.email.sign-in.failed"));
        }
    }

    private void validateUserIsActive(SignInDto signInDto, User user) {
        if (!user.isActive()) {
            LOGGER.warn("User is blocked for email: " + signInDto.getEmail());
            throw new InvalidUserInputException(
                    SIGN_IN_JSP, Collections.singletonMap(EMAIL, "action.email.blocked"));
        }
    }

    private void validatePasswordIsValid(SignInDto signInDto, User user) {
        if (!passwordEncoder.match(signInDto.getPassword(), user.getPassword())) {
            LOGGER.warn("Password not valid for " + signInDto.getEmail());
            throw new InvalidUserInputException(
                    SIGN_IN_JSP, Collections.singletonMap(EMAIL, "action.email.sign-in.failed"));
        }
    }
}
