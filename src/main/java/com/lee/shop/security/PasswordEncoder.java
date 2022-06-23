package com.lee.shop.security;

public interface PasswordEncoder {

    String encode(String password);

    default boolean match(String typedPassword, String encodedPassword) {
        return encode(typedPassword).equals(encodedPassword);
    }
}
