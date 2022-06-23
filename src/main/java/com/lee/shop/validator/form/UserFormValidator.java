package com.lee.shop.validator.form;

import com.lee.shop.Constants;
import com.lee.shop.dao.UserDao;
import com.lee.shop.model.entity.User;
import com.lee.shop.model.form.UserForm;
import com.lee.shop.validator.FormValidator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserFormValidator implements FormValidator<UserForm> {

    private final UserDao userDao;

    private final boolean signUpMode;

    public UserFormValidator(UserDao userDao, boolean signUpMode) {
        this.userDao = userDao;
        this.signUpMode = signUpMode;
    }

    @Override
    public Map<String, String> getErrors(UserForm form) {
        Map<String, String> map = new HashMap<>();
        if (signUpMode) {
            if (form.getEmail() == null || form.getEmail().trim().isEmpty()) {
                map.put(UserForm.EMAIL, "action.email.is.required");
            } else {
                if (!Constants.EMAIL_PATTERN.matcher(form.getEmail()).matches()) {
                    map.put(UserForm.EMAIL, "action.email.has.invalid.format");
                } else {
                    User user = userDao.getByEmail(form.getEmail());
                    if (user != null) {
                        map.put(UserForm.EMAIL, "action.email.already.registered");
                    }
                }
            }
            if (form.getPassword() == null || form.getPassword().trim().isEmpty()) {
                map.put(UserForm.PASSWORD, "action.password.is.required");
            }
        }
        if (form.getFirstname() == null || form.getFirstname().trim().isEmpty()) {
            map.put(UserForm.FIRSTNAME, "action.firstname.is.required");
        }
        if (form.getLastname() == null || form.getLastname().trim().isEmpty()) {
            map.put(UserForm.LASTNAME, "action.lastname.is.required");
        }
        if (form.isPasswordPresent() || signUpMode) {
            if (form.getPassword2() == null || form.getPassword2().trim().isEmpty()) {
                map.put(UserForm.PASSWORD2, "action.password2.is.required");
            } else if (!Objects.equals(form.getPassword(), form.getPassword2())) {
                map.put(UserForm.PASSWORD2, "action.password2.not.match.password");
            }
        }
        return map;
    }
}
