package com.lee.shop.validator.dto;

import com.lee.shop.Constants;
import com.lee.shop.dao.UserDao;
import com.lee.shop.model.dto.UserDto;
import com.lee.shop.model.entity.User;
import com.lee.shop.validator.DtoValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserDtoValidator implements DtoValidator<UserDto> {

    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String PASSWORD2 = "password2";

    private final UserDao userDao;

    private final boolean signUpMode;

    public UserDtoValidator(UserDao userDao, boolean signUpMode) {
        this.userDao = userDao;
        this.signUpMode = signUpMode;
    }

    @Override
    public Map<String, String> getErrors(UserDto userDto) {
        Map<String, String> map = new HashMap<>();
        if (signUpMode) {
            if (userDto.getEmail() == null || userDto.getEmail().trim().isEmpty()) {
                map.put(EMAIL, "action.email.is.required");
            } else {
                if (!Constants.EMAIL_PATTERN.matcher(userDto.getEmail()).matches()) {
                    map.put(EMAIL, "action.email.has.invalid.format");
                } else {
                    User user = userDao.getByEmail(userDto.getEmail());
                    if (user != null) {
                        map.put(EMAIL, "action.email.already.registered");
                    }
                }
            }
            if (userDto.getPassword() == null || userDto.getPassword().trim().isEmpty()) {
                map.put(PASSWORD, "action.password.is.required");
            }
        }
        verifyThatFirstNameIsNotBlank(userDto, map);
        if (userDto.getLastname() == null || userDto.getLastname().trim().isEmpty()) {
            map.put(LASTNAME, "action.lastname.is.required");
        }
        if (userDto.isPasswordPresent() || signUpMode) {
            if (userDto.getPassword2() == null || userDto.getPassword2().trim().isEmpty()) {
                map.put(PASSWORD2, "action.password2.is.required");
            } else if (!Objects.equals(userDto.getPassword(), userDto.getPassword2())) {
                map.put(PASSWORD2, "action.password2.not.match.password");
            }
        }
        return map;
    }

    private void verifyThatFirstNameIsNotBlank(UserDto userDto, Map<String, String> map) {
        if (userDto.getFirstname() == null || userDto.getFirstname().trim().isEmpty()) {
            map.put(FIRSTNAME, "action.firstname.is.required");
        }
    }
}
