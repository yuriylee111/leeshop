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

    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String PASSWORD2 = "password2";

    private static final String ACTION_EMAIL_IS_REQUIRED_TEMPLATE = "action.email.is.required";
    private static final String ACTION_EMAIL_HAS_INVALID_FORMAT_TEMPLATE = "action.email.has.invalid.format";
    private static final String ACTION_EMAIL_ALREADY_REGISTERED_TEMPLATE = "action.email.already.registered";
    private static final String ACTION_FIRSTNAME_IS_REQUIRED_TEMPLATE = "action.firstname.is.required";
    private static final String ACTION_LASTNAME_IS_REQUIRED_TEMPLATE = "action.lastname.is.required";
    private static final String ACTION_PASSWORD_IS_REQUIRED_TEMPLATE = "action.password.is.required";
    private static final String ACTION_PASSWORD_2_IS_REQUIRED_TEMPLATE = "action.password2.is.required";
    private static final String ACTION_PASSWORD_2_NOT_MATCH_PASSWORD_TEMPLATE = "action.password2.not.match.password";

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
            validateEmailIsRequired(userDto, errorsMap);
            validateEmailIsValid(userDto, errorsMap);
            validateEmailIsExists(userDto, errorsMap);
            validatePasswordIsRequired(userDto, errorsMap);
        }
        validateFirstNameIsNotBlank(userDto, errorsMap);
        validateLastNameIsNotBlank(userDto, errorsMap);
        validatePasswordsMatch(userDto, errorsMap);
        if (!errorsMap.isEmpty()) {
            throw new InvalidUserInputException(jspName, errorsMap, userDto);
        }
    }

    private void validateEmailIsRequired(UserDto userDto, Map<String, String> errorsMap) {
        if (userDto.getEmail() == null || userDto.getEmail().trim().isEmpty()) {
            errorsMap.put(EMAIL, ACTION_EMAIL_IS_REQUIRED_TEMPLATE);
        }
    }

    private void validateEmailIsValid(UserDto userDto, Map<String, String> errorsMap) {
        if (!Constants.EMAIL_PATTERN.matcher(userDto.getEmail()).matches()) {
            errorsMap.put(EMAIL, ACTION_EMAIL_HAS_INVALID_FORMAT_TEMPLATE);
        }
    }

    private void validateEmailIsExists(UserDto userDto, Map<String, String> errorsMap) {
        User user = userDao.getByEmail(userDto.getEmail());
        if (user != null) {
            errorsMap.put(EMAIL, ACTION_EMAIL_ALREADY_REGISTERED_TEMPLATE);
        }
    }

    private void validatePasswordIsRequired(UserDto userDto, Map<String, String> errorsMap) {
        if (userDto.getPassword() == null || userDto.getPassword().trim().isEmpty()) {
            errorsMap.put(PASSWORD, ACTION_PASSWORD_IS_REQUIRED_TEMPLATE);
        }
    }

    private void validateFirstNameIsNotBlank(UserDto userDto, Map<String, String> errorsMap) {
        if (userDto.getFirstname() == null || userDto.getFirstname().trim().isEmpty()) {
            errorsMap.put(FIRSTNAME, ACTION_FIRSTNAME_IS_REQUIRED_TEMPLATE);
        }
    }

    private void validateLastNameIsNotBlank(UserDto userDto, Map<String, String> errorsMap) {
        if (userDto.getLastname() == null || userDto.getLastname().trim().isEmpty()) {
            errorsMap.put(LASTNAME, ACTION_LASTNAME_IS_REQUIRED_TEMPLATE);
        }
    }

    private void validatePasswordsMatch(UserDto userDto, Map<String, String> errorsMap) {
        if (userDto.isPasswordPresent() || signUpMode) {
            if (userDto.getPassword2() == null || userDto.getPassword2().trim().isEmpty()) {
                errorsMap.put(PASSWORD2, ACTION_PASSWORD_2_IS_REQUIRED_TEMPLATE);
            } else if (!Objects.equals(userDto.getPassword(), userDto.getPassword2())) {
                errorsMap.put(PASSWORD2, ACTION_PASSWORD_2_NOT_MATCH_PASSWORD_TEMPLATE);
            }
        }
    }
}
