package com.lee.shop.model.mapper;

import com.lee.shop.model.dto.SignInDto;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestToSignInDtoMapper {

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    public SignInDto map(HttpServletRequest request) {
        return new SignInDto(request.getParameter(EMAIL), request.getParameter(PASSWORD));
    }
}
