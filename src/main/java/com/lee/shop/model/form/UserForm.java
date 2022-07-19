package com.lee.shop.model.form;

import com.lee.shop.model.entity.User;

import javax.servlet.http.HttpServletRequest;

public class UserForm {

    public static final String ID = "id";
    public static final String FIRSTNAME = "firstname";
    public static final String EMAIL = "email";
    public static final String LASTNAME = "lastname";
    public static final String PASSWORD = "password";
    public static final String PASSWORD2 = "password2";
    public static final String PHONE = "phone";

    private final Long id;
    private final String email;
    private final String firstname;
    private final String lastname;
    private final String password;
    private final String password2;
    private final String phone;

    public UserForm() {
        this.id = null;
        this.email = null;
        this.firstname = null;
        this.lastname = null;
        this.password = null;
        this.password2 = null;
        this.phone = null;
    }

    public UserForm(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.password = null;
        this.password2 = null;
        this.phone = user.getPhone();
    }

    public UserForm(HttpServletRequest request) {
        String id = request.getParameter(ID);
        if (id != null) {
            this.id = Long.parseLong(id);
        } else {
            this.id = null;
        }
        this.email = request.getParameter(EMAIL);
        this.firstname = request.getParameter(FIRSTNAME);
        this.lastname = request.getParameter(LASTNAME);
        this.password = request.getParameter(PASSWORD);
        this.password2 = request.getParameter(PASSWORD2);
        this.phone = request.getParameter(PHONE);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public boolean isPasswordPresent() {
        return password != null && !password.trim().isEmpty();
    }

    public String getPassword() {
        return password;
    }

    public String getPassword2() {
        return password2;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "UserForm{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", password2='" + password2 + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
