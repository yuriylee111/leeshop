package com.lee.shop.validator.logic;

import com.lee.shop.model.entity.User;
import com.lee.shop.model.form.SignInForm;
import com.lee.shop.security.PasswordEncoder;
import com.lee.shop.validator.LogicValidator;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SignInLogicValidator implements LogicValidator<SignInForm, User> {

    private static final Logger LOGGER = Logger.getLogger(SignInLogicValidator.class);

    private static final String EMAIL = "email";

    private final PasswordEncoder passwordEncoder;

    public SignInLogicValidator(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Map<String, String> getErrors(SignInForm form, User user) {
        Map<String, String> map = new HashMap<>();
        if (user == null) {
            map.put(EMAIL, "action.email.sign-in.failed");
            LOGGER.warn("User not found by email: " + form.getEmail());
        } else if (!user.isActive()) {
            map.put(EMAIL, "action.email.blocked");
            LOGGER.warn("User is blocked for email: " + form.getEmail());
        } else if (!passwordEncoder.match(form.getPassword(), user.getPassword())) {
            map.put(EMAIL, "action.email.sign-in.failed");
            LOGGER.warn("Password not valid for " + form.getEmail());
        }
        return map;
    }
}
