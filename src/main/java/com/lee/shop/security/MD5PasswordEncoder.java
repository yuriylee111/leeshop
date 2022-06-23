package com.lee.shop.security;

import com.lee.shop.exception.ApplicationException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5PasswordEncoder implements PasswordEncoder {

    private static final String MD5 = "MD5";

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
            throw new ApplicationException("Algorithm not found: " + MD5, exception);
        }
    }

    private String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
