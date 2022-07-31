package com.lee.shop.security;

import com.lee.shop.exception.ApplicationException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5PasswordEncoder implements PasswordEncoder {

    private static final String MD5 = "MD5";
    private static final String ALGORITHM_NOT_FOUND_TEMPLATE = "Algorithm not found: %s";
    private static final String BYTE_TO_HEX_TEMPLATE = "%02x";

    @Override
    public String encode(String password) {
        MessageDigest md = createMessageDigest();
        md.update(password.getBytes());
        return byteArrayToHex(md.digest());
    }

    private MessageDigest createMessageDigest() {
        try {
            return MessageDigest.getInstance(MD5);
        } catch (NoSuchAlgorithmException exception) {
            throw new ApplicationException(String.format(ALGORITHM_NOT_FOUND_TEMPLATE, MD5), exception);
        }
    }

    private String byteArrayToHex(byte[] byteArray) {
        StringBuilder sb = new StringBuilder(byteArray.length * 2);
        for (byte b : byteArray)
            sb.append(String.format(BYTE_TO_HEX_TEMPLATE, b));
        return sb.toString();
    }
}
