package com.lee.shop.model.mapper;

import com.lee.shop.model.dto.UserDto;
import com.lee.shop.model.entity.User;

public class UserToUserDtoMapper {

    public UserDto map(User user) {
        return new UserDto.Builder()
                .setId(user.getId())
                .setFirstname(user.getFirstname())
                .setLastname(user.getLastname())
                .setEmail(user.getEmail())
                .setPhone(user.getPhone())
                .build();
    }
}
