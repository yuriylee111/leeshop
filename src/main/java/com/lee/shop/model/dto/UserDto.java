package com.lee.shop.model.dto;

public class UserDto {

    private final Long id;
    private final String email;
    private final String firstname;
    private final String lastname;
    private final String password;
    private final String password2;
    private final String phone;

    private UserDto(Long id, String email, String firstname, String lastname, String password, String password2, String phone) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.password2 = password2;
        this.phone = phone;
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
        return "UserDto{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", password2='" + password2 + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public static final class Builder {

        private Long id;
        private String firstname;
        private String lastname;
        private String email;
        private String password;
        private String password2;
        private String phone;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setFirstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public Builder setLastname(String lastname) {
            this.lastname = lastname;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setPassword2(String password2) {
            this.password2 = password2;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public UserDto build() {
            return new UserDto(id, email, firstname, lastname, password, password2, phone);
        }
    }
}
