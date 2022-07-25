package com.lee.shop.model.mapper;

import com.lee.shop.model.dto.UserDto;
import com.lee.shop.model.entity.User;
import com.lee.shop.model.enumeration.Role;
import com.lee.shop.security.PasswordEncoder;

public class UserDtoToUserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserDtoToUserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User map(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        mapCommonFields(user, userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);
        return user;
    }

    public User map(User user, UserDto userDto) {
        mapCommonFields(user, userDto);
        if (userDto.isPasswordPresent()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        return user;
    }

    private void mapCommonFields(User user, UserDto userDto) {
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setPhone(userDto.getPhone());

    }
}
