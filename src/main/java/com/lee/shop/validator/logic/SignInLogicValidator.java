package com.lee.shop.validator.logic;

import com.lee.shop.exception.InvalidUserInputException;
import com.lee.shop.model.dto.SignInDto;
import com.lee.shop.model.entity.User;
import com.lee.shop.security.PasswordEncoder;
import com.lee.shop.validator.LogicValidator;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

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
        Map<String, String> errorsMap = new HashMap<>();
        if (user == null) {
            errorsMap.put(EMAIL, "action.email.sign-in.failed");
            LOGGER.warn("User not found by email: " + signInDto.getEmail());
        } else if (!user.isActive()) {
            errorsMap.put(EMAIL, "action.email.blocked");
            LOGGER.warn("User is blocked for email: " + signInDto.getEmail());
        } else if (!passwordEncoder.match(signInDto.getPassword(), user.getPassword())) {
            errorsMap.put(EMAIL, "action.email.sign-in.failed");
            LOGGER.warn("Password not valid for " + signInDto.getEmail());
        }
        if (!errorsMap.isEmpty()) {
            throw new InvalidUserInputException(SIGN_IN_JSP, errorsMap);
        }
    }
}
