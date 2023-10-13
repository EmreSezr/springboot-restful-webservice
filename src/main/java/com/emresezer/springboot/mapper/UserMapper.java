package com.emresezer.springboot.mapper;

import com.emresezer.springboot.dto.UserDto;
import com.emresezer.springboot.entity.User;

public class UserMapper {

    //Convert User JPA Entity into UserDto
    public static UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
        return userDto;
    }

    // Conver UserDto into User JPA Entity
    public static User mapToUser(UserDto userDto) {
        User user = new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail()
        );
        return user;
    }
}
