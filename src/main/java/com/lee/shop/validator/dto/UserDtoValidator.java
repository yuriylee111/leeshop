package com.lee.shop.validator.dto;

import com.lee.shop.Constants;
import com.lee.shop.dao.UserDao;
import com.lee.shop.exception.InvalidUserInputException;
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

    private final String jspName;

    public UserDtoValidator(UserDao userDao, boolean signUpMode, String jspName) {
        this.userDao = userDao;
        this.signUpMode = signUpMode;
        this.jspName = jspName;
    }

    @Override
    public void validate(UserDto userDto) {
        Map<String, String> errorsMap = new HashMap<>();
        if (signUpMode) {
            if (userDto.getEmail() == null || userDto.getEmail().trim().isEmpty()) {
                errorsMap.put(EMAIL, "action.email.is.required");
            } else {
                if (!Constants.EMAIL_PATTERN.matcher(userDto.getEmail()).matches()) {
                    errorsMap.put(EMAIL, "action.email.has.invalid.format");
                } else {
                    User user = userDao.getByEmail(userDto.getEmail());
                    if (user != null) {
                        errorsMap.put(EMAIL, "action.email.already.registered");
                    }
                }
            }
            if (userDto.getPassword() == null || userDto.getPassword().trim().isEmpty()) {
                errorsMap.put(PASSWORD, "action.password.is.required");
            }
        }
        verifyThatFirstNameIsNotBlank(userDto, errorsMap);
        if (userDto.getLastname() == null || userDto.getLastname().trim().isEmpty()) {
            errorsMap.put(LASTNAME, "action.lastname.is.required");
        }
        if (userDto.isPasswordPresent() || signUpMode) {
            if (userDto.getPassword2() == null || userDto.getPassword2().trim().isEmpty()) {
                errorsMap.put(PASSWORD2, "action.password2.is.required");
            } else if (!Objects.equals(userDto.getPassword(), userDto.getPassword2())) {
                errorsMap.put(PASSWORD2, "action.password2.not.match.password");
            }
        }
        if (!errorsMap.isEmpty()) {
            throw new InvalidUserInputException(jspName, errorsMap, userDto);
        }
    }

    private void verifyThatFirstNameIsNotBlank(UserDto userDto, Map<String, String> map) {
        if (userDto.getFirstname() == null || userDto.getFirstname().trim().isEmpty()) {
            map.put(FIRSTNAME, "action.firstname.is.required");
        }
    }
}
