package com.electronic.store.services.impl;

import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.UserDto;
import com.electronic.store.entites.User;
import com.electronic.store.exceptions.ResourceNotFoundException;
import com.electronic.store.helper.AppConstants;
import com.electronic.store.helper.Helper;
import com.electronic.store.repositories.UserRepository;
import com.electronic.store.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Value("${user.profile.image.path}")
    private String imageUplodPath;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    /**
     * @param userDto
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This method is used to create User
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
     * @param userDto
     * @param userId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote THis Method is used for Update User
     */

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        log.info("Initiated Dao call for update User with UserId:{}", userId);
        User updateduser = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", AppConstants.USER_NOT_FOUND + userId));
        UserDto updaedDto = entityToDto(updateduser);
        log.info("Completed Dao call for update User with UserId:{}", userId);
        return updaedDto;
    }

    /**
     * @return
     * @author Shubham Dhokchaule
     * @apiNote THis Method is used for get All Users
     */
    @Override
    public PageableResponse<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        log.info("Initiated Dao call for get All  Users with pageNumber,pageSize,sortBy,sortDir:{}", pageNumber, pageSize, sortBy, sortDir);
        Page<User> page = this.userRepository.findAll(pageable);
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
        log.info("Completed Dao call for get All  Users with pageNumber,pageSize,sortBy,sortDir:{}", pageNumber, pageSize, sortBy, sortDir);
        return response;
    }

    /**
     * @param userId
     * @author Shubham Dhokchaule
     * @apiNote This method is used for Delete User With userId
     */
    @Override
    public void deleteUser(String userId) {
        log.info("Initiated Dao call for Delete User With userId:{}", userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", AppConstants.USER_NOT_FOUND + userId));
        String fullPath = imageUplodPath + user.getImageName();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException e) {
            log.error("Image not found in Folder..:{}", e.getMessage());
        } catch (IOException e) {
            log.error("Unable to found image:{}", e.getMessage());
        }
        userRepository.delete(user);
        log.info("Completed Dao call for Delete User With userId:{}", userId);

    }

    /**
     * @param userId
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This Is Method is Used for get User By userId
     */
    @Override
    public UserDto getUserById(String userId) {
        log.info("Initiated Dao call for get  User With userId:{}", userId);
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", AppConstants.USER_NOT_FOUND + userId));
        log.info("Completed Dao call for get  User With userId:{}", userId);
        return entityToDto(user);
    }

    /**
     * @param email
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This Is Method is Used for get User By email
     */
    @Override
    public UserDto getUserByEmail(String email) {
        log.info("Initiated Dao call for get  User With email:{}", email);
        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user", AppConstants.USER_NOT_FOUND + email));
        log.info("Completed Dao call for get  User With email:{}", email);
        return entityToDto(user);
    }

    /**
     * @param keyword
     * @return
     * @author Shubham Dhokchaule
     * @apiNote This Is Method is Used for get User By keyword
     */
    public List<UserDto> searchUser(String keyword) {
        log.info("Initiated Dao call for get  User With keyword:{}", keyword);
        List<User> users = this.userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        log.info("Completed Dao call for get  User With keyword:{}", keyword);
        return dtoList;
    }

    /**
     * @param userDto
     * @return
     */
    @Override
    public UserDto registerNewUser(UserDto userDto) {
        return null;
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
