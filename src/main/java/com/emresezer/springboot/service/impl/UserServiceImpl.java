package com.emresezer.springboot.service.impl;

import com.emresezer.springboot.dto.UserDto;
import com.emresezer.springboot.entity.User;
import com.emresezer.springboot.exception.EmailAlreadyExistException;
import com.emresezer.springboot.exception.ResourceNotFoundException;
import com.emresezer.springboot.mapper.AutoUserMapper;
import com.emresezer.springboot.repository.UserRepository;
import com.emresezer.springboot.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    private ModelMapper modelMapper;


    @Override
    public UserDto createUser(UserDto userDto) {

        //Convert UserDto into User JPA Entity
        //User user = UserMapper.mapToUser(userDto);

        // User user = modelMapper.map(userDto, User.class);


        /**
         * Create user if email already in database throw error
         * */
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new EmailAlreadyExistException("Email Already Exists for User");
        }
        User user = AutoUserMapper.MAPPER.mapToUser(userDto);


        User savedUser = userRepository.save(user);

        //Convert User JPA entity to UserDto
        //UserDto savedUserDto = UserMapper.mapToUserDto(savedUser);

        //UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
        UserDto savedUserDto = AutoUserMapper.MAPPER.mapToUserDto(savedUser);

        return savedUserDto;
    }

    @Override
    public UserDto getUserById(Long userId) {
        /**Find the user by id if we dont have that id throw error*/
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId));

        //return UserMapper.mapToUserDto(user);

        //return modelMapper.map(user, UserDto.class);
        return AutoUserMapper.MAPPER.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        //return users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList());

        //return users.stream().map((user) -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

        return users
                .stream()
                .map((user) -> AutoUserMapper.MAPPER.mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto user) {
        /** Update user find by User id if we dont have userId throw error  */
        User existedUser = userRepository.findById(user.getId()).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", user.getId())
        );
        existedUser.setFirstName(user.getFirstName());
        existedUser.setLastName(user.getLastName());
        existedUser.setEmail(user.getEmail());
        User updatedUser = userRepository.save(existedUser);

        //UserDto userDto = UserMapper.mapToUserDto(updatedUser);

        //UserDto userDto = modelMapper.map(updatedUser, UserDto.class);

        UserDto userDto = AutoUserMapper.MAPPER.mapToUserDto(updatedUser);
        return userDto;
    }

    @Override
    public void deleteUser(Long userId) {
        /**Delete by User Id, if We dont have that userId throw error */
        User existingUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", userId)
        );

        userRepository.deleteById(userId);
    }
}
