package com.lcwd.electroic.store.services.impl;

import com.lcwd.electroic.store.dtos.UserDto;
import com.lcwd.electroic.store.entites.User;
import com.lcwd.electroic.store.repositories.UserRepository;
import com.lcwd.electroic.store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDto craeteUser(UserDto userDto) {

        //generate Unique user id String format
        String userId = UUID.randomUUID().toString();
 userDto.setUserId(userId);
        //dto-userEntity
        User user=dtoToEntity(userDto);
        User savedUser = userRepository.save(user);

        //Entity -Dto

        UserDto newDto=entityToDto(savedUser);

        return newDto;
    }


    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User updateduser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found With Given Id"));
        UserDto updaedDto = entityToDto(updateduser);
        return updaedDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found With given ID..!!"));
    userRepository.delete(user);
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not Found With ID.."));
        return entityToDto(user);
    }

    @Override
    public UserDto getUserByEmail(String userId) {
        return null;
    }

    @Override
    public UserDto searchUser(String keyword) {

        return null;
    }

    private UserDto entityToDto(User savedUser) {

        UserDto userDto = UserDto.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .gender(savedUser.getGender())
                .password(savedUser.getPassword())
                .about(savedUser.getAbout())
                .imageName(savedUser.getImageName())
                .build();


        return userDto;
    }
    private User dtoToEntity(UserDto userDto) {
        User user = User.builder().userId(userDto.getUserId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .gender(userDto.getGender())
                .password(userDto.getPassword())
                .about(userDto.getAbout())
                .imageName(userDto.getImageName())
                .build();
        return user;
    }


}
