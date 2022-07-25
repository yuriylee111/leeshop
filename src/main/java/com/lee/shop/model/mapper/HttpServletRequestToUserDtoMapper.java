package com.lee.shop.model.mapper;

import com.lee.shop.model.dto.UserDto;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestToUserDtoMapper {

    public static final String ID = "id";
    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String PASSWORD2 = "password2";
    public static final String PHONE = "phone";

    public UserDto map(HttpServletRequest request) {
        UserDto.Builder builder = new UserDto.Builder();
        String id = request.getParameter(ID);
        if (id != null) {
            builder.setId(Long.parseLong(id));
        }
        return builder
                .setFirstname(request.getParameter(FIRSTNAME))
                .setLastname(request.getParameter(LASTNAME))
                .setEmail(request.getParameter(EMAIL))
                .setPassword(request.getParameter(PASSWORD))
                .setPassword2(request.getParameter(PASSWORD2))
                .setPhone(request.getParameter(PHONE))
                .build();
    }
}
