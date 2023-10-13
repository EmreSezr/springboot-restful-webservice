package com.emresezer.springboot.mapper;

import com.emresezer.springboot.dto.UserDto;
import com.emresezer.springboot.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoUserMapper {

    //@Mapping(source = "email", target = "emailAddress")
    AutoUserMapper MAPPER = Mappers.getMapper(AutoUserMapper.class);


    UserDto mapToUserDto(User user);

    User mapToUser(UserDto userDto);
}
