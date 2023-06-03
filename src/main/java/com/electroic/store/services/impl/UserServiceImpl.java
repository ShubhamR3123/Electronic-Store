package com.electroic.store.services.impl;

import com.electroic.store.dtos.UserDto;
import com.electroic.store.entites.User;
import com.electroic.store.exceptions.ResourceNotFoundException;
import com.electroic.store.helper.AppConstants;
import com.electroic.store.repositories.UserRepository;
import com.electroic.store.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * @author Shubham Dhokchaule
     * @apiNote This method is used to create User
     * @param userDto
     * @return

     */
    @Override
    public UserDto craeteUser(UserDto userDto) {
        log.info("Initiated Dao call for create user");
        //generate Unique user id String format
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        //dto-userEntity
        User user = dtoToEntity(userDto);
        User savedUser = this.userRepository.save(user);
        //Entity -Dto
        UserDto newDto = entityToDto(savedUser);
        log.info("Completed Dao call for create user");
        return newDto;
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote THis Method is used for Update User
     * @param userDto
     * @param userId
     * @return
     */

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        log.info("Initiated Dao call for update User with UserId:{}",userId);
        User updateduser = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + userId));
        UserDto updaedDto = entityToDto(updateduser);
        log.info("Completed Dao call for update User with UserId:{}",userId);
        return updaedDto;
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote THis Method is used for get All Users
     * @return
     */
    @Override
    public List<UserDto> getAllUsers() {
        log.info("Initiated Dao call for get All  Users ");
        List<User> users =this.userRepository.findAll();
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        log.info("Completed Dao call for get All  Users ");
        return dtoList;
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote This method is used for Delete User With userId
     * @param userId
     */
    @Override
    public void deleteUser(String userId) {
        log.info("Initiated Dao call for Delete User With userId:{}",userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + userId));
        userRepository.delete(user);
        log.info("Completed Dao call for Delete User With userId:{}",userId);

    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote  This Is Method is Used for get User By userId
     * @param userId
     * @return
     */
    @Override
    public UserDto getUserById(String userId) {
        log.info("Initiated Dao call for get  User With userId:{}",userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + userId));
        log.info("Completed Dao call for get  User With userId:{}",userId);
        return entityToDto(user);
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote This Is Method is Used for get User By email
     * @param email
     * @return
     */
    @Override
    public UserDto getUserByEmail(String email) {
        log.info("Initiated Dao call for get  User With email:{}",email);
        User user =this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(AppConstants.USER_NOT_FOUND + email));
        log.info("Completed Dao call for get  User With email:{}",email);
        return entityToDto(user);
    }

    /**
     * @author Shubham Dhokchaule
     * @apiNote This Is Method is Used for get User By keyword
     * @param keyword
     * @return
     */
    public List<UserDto> searchUser(String keyword) {
        log.info("Initiated Dao call for get  User With keyword:{}",keyword);
        List<User> users = this.userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        log.info("Completed Dao call for get  User With keyword:{}",keyword);
        return dtoList;
    }


    private UserDto entityToDto(User savedUser) {

//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .gender(savedUser.getGender())
//                .password(savedUser.getPassword())
//                .about(savedUser.getAbout())
//                .imageName(savedUser.getImageName())
//                .build();


        return this.modelMapper.map(savedUser, UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
//        User user = User.builder().userId(userDto.getUserId())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .gender(userDto.getGender())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .imageName(userDto.getImageName())
//                .build();
        return this.modelMapper.map(userDto, User.class);
    }


}
