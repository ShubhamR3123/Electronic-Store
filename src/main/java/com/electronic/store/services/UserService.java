package com.electronic.store.services;

import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.UserDto;

import java.util.List;

public interface UserService {

    //create

    UserDto craeteUser(UserDto userDto);

    //update
    UserDto updateUser(UserDto userDto, String userId);

    //get All users
    PageableResponse<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    // delete User
    void deleteUser(String userId);

    //Get Single user By id
    UserDto getUserById(String userId);

    //get Single user By Email
    UserDto getUserByEmail(String userId);

    //serach image by keyword
    List<UserDto> searchUser(String keyword);

}
