package com.lcwd.electroic.store.services;

import com.lcwd.electroic.store.dtos.UserDto;

import java.util.List;

public interface UserService {

    //create

    UserDto craeteUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto, String userId);

    //get All users
    List<UserDto> getAllUsers();

    // delete User
     void deleteUser(String userId);

    //Get Single user By id
    UserDto getUserById(String userId);

    //get Single user By Email
    UserDto getUserByEmail(String userId);

    //serach image by keyword
    UserDto searchUser(String keyword);

}
