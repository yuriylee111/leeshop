package com.lee.shop.validator.logic;

import com.lee.shop.model.dto.SignInDto;
import com.lee.shop.model.entity.User;
import com.lee.shop.security.PasswordEncoder;
import com.lee.shop.validator.LogicValidator;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SignInLogicValidator implements LogicValidator<SignInDto, User> {

    private static final Logger LOGGER = Logger.getLogger(SignInLogicValidator.class);

    private static final String EMAIL = "email";

    private final PasswordEncoder passwordEncoder;

    public SignInLogicValidator(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Map<String, String> getErrors(SignInDto signInDto, User user) {
        Map<String, String> map = new HashMap<>();
        if (user == null) {
            map.put(EMAIL, "action.email.sign-in.failed");
            LOGGER.warn("User not found by email: " + signInDto.getEmail());
        } else if (!user.isActive()) {
            map.put(EMAIL, "action.email.blocked");
            LOGGER.warn("User is blocked for email: " + signInDto.getEmail());
        } else if (!passwordEncoder.match(signInDto.getPassword(), user.getPassword())) {
            map.put(EMAIL, "action.email.sign-in.failed");
            LOGGER.warn("Password not valid for " + signInDto.getEmail());
        }
        return map;
    }
}
