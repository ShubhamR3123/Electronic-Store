package com.electronic.store.controllers;

import com.electronic.store.dtos.ApiResponseMessage;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.UserDto;
import com.electronic.store.helper.AppConstants;
import com.electronic.store.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @param userDto
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This Api is used for create User
     */
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Initiated request for create User details");
        UserDto userDto1 = this.userService.craeteUser(userDto);
        log.info("Completed request for create User details");
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    /**
     * @param userDto
     * @param userId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This Api is used to update User
     */
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") String userId) {
        log.info("Initiated request for update User details with userId:{}", userId);
        UserDto updateUser = this.userService.updateUser(userDto, userId);
        log.info("Completed request for update User details with userId:{}", userId);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }

    /**
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This Api is used to Get All Users
     */
    @GetMapping("/")
    public ResponseEntity<PageableResponse<UserDto>>getAllUsers(
            @RequestParam(value = "pageNumber",defaultValue = "1",required = false)Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir){

        log.info("Initiated request for get All User details ");
        PageableResponse<UserDto> allUsers = this.userService.getAllUsers(pageNumber, pageSize,sortBy,sortDir);

        log.info("Completed request for get All User details ");
        return new ResponseEntity<PageableResponse<UserDto>>(allUsers, HttpStatus.OK);
    }

    /**
     * @param userId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This Api is used to Delete User with userId
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
     * @param userId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This Api is used to Get Single User With Id
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        log.info("Initiated request for get user details with userId:{}", userId);
        UserDto userById = this.userService.getUserById(userId);
        log.info("Completed request for get user details with userId:{}", userId);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    /**
     * @param email
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This Api is used to Get User By Email
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        log.info("Initiated request for get user details with email:{}", email);
        UserDto userByEmail = this.userService.getUserByEmail(email);
        log.info("Completed request for get user details with email:{}", email);
        return new ResponseEntity<>(userByEmail, HttpStatus.OK);
    }

    /**
     * @param keyword
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This Api is used to Search User With Keyword
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable("keyword") String keyword) {
        log.info("Initiated request for search user details with keyword:{}", keyword);
        List<UserDto> user = this.userService.searchUser(keyword);
        log.info("Completed request for search user details with keyword:{}", keyword);
        return new ResponseEntity<List<UserDto>>(user, HttpStatus.OK);
    }


}
