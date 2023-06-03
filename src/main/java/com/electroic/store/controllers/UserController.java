package com.electroic.store.controllers;

import com.electroic.store.dtos.ApiResponseMessage;
import com.electroic.store.dtos.UserDto;
import com.electroic.store.helper.AppConstants;
import com.electroic.store.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @author Shubham Dhokchaule
     * @apiNote This Api is used for create User
     * @param userDto
     * @return
     */
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        log.info("Initiated request for create User details");
        UserDto userDto1 = this.userService.craeteUser(userDto);
        log.info("Completed request for create User details");
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote This Api is used to update User
     * @param userDto
     * @param userId
     * @return
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("userId") String userId) {
        log.info("Initiated request for update User details with userId:{}", userId);
        UserDto updateUser = this.userService.updateUser(userDto, userId);
        log.info("Completed request for update User details with userId:{}", userId);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote This Api is used to Get All Users
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.info("Initiated request for get All User details ");
        List<UserDto> allUsers = this.userService.getAllUsers();
        log.info("Completed request for get All User details ");
        return new ResponseEntity<List<UserDto>>(allUsers, HttpStatus.OK);
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote This Api is used to Delete User with userId
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId) {
        log.info("Initiated request for delete user  details with userId:{}", userId);
        this.userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage.builder().message(AppConstants.USER_DELETED).success(true).status(HttpStatus.OK).build();
        log.info("Completed request for delete user  details with userId:{}", userId);
        return new ResponseEntity<ApiResponseMessage>(message, HttpStatus.OK);
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote This Api is used to Get Single User With Id
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        log.info("Initiated request for get user details with userId:{}", userId);
        UserDto userById = this.userService.getUserById(userId);
        log.info("Completed request for get user details with userId:{}", userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote This Api is used to Get User By Email
     * @param email
     * @return
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        log.info("Initiated request for get user details with email:{}", email);
        UserDto userByEmail = this.userService.getUserByEmail(email);
        log.info("Completed request for get user details with email:{}", email);
        return new ResponseEntity<>(userByEmail, HttpStatus.OK);
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote This Api is used to Search User With Keyword
     * @param keyword
     * @return
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable("keyword") String keyword) {
        log.info("Initiated request for search user details with keyword:{}", keyword);
        List<UserDto> user =this.userService.searchUser(keyword);
        log.info("Completed request for search user details with keyword:{}", keyword);
        return new ResponseEntity<List<UserDto>>(user, HttpStatus.OK);
    }


}
