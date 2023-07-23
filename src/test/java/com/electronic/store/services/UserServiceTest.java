package com.electronic.store.services;

import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.UserDto;
import com.electronic.store.entites.User;
import com.electronic.store.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private ModelMapper mapper;
    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    User user;

    @BeforeEach
    public void init() {
        user = User.builder().name("Shubham").email("S@gmail.com").password("abc").gender("Male").about("Software Engineer").imageName("xyz.png").build();


    }

    @Test
    public void createUserTest() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto user1 = userService.craeteUser(mapper.map(user, UserDto.class));
        System.out.println(user1.getName());

        Assertions.assertNotNull(user1);
        Assertions.assertEquals("Shubham", user1.getName());

    }

    @Test
    public void updateUser() {

        String userId = "shbhh";

        UserDto userDto = UserDto.builder().name("Ajay Leprechaun").password("xyz").gender("Male").about("Software Engineer Java Developer").imageName("abc.png").build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto updatedUser = userService.updateUser(userDto, userId);
        System.out.println(updatedUser.getName());
        System.out.println(updatedUser.getAbout());
        System.out.println(updatedUser.getImageName());
        Assertions.assertNotNull(userDto);
        // Assertions.assertEquals(userDto.getName(),updatedUser.getName());
    }


    @Test
    public void getAllUsersTest() {

        User user1 = User.builder().name("Shubham").email("S@gmail.com").password("abc").gender("Male").about("Software Engineer").imageName("xyz.png").build();
        User user2 = User.builder().name("Shubham").email("S@gmail.com").password("abc").gender("Male").about("Software Engineer").imageName("xyz.png").build();

        List<User> userList = Arrays.asList(user, user1, user2);

        Page<User> page = new PageImpl<>(userList);
        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<UserDto> allUsers = userService.getAllUsers(1, 2, "name", "asc");
        Assertions.assertEquals(3, allUsers.getContent().size());

    }

    @Test
    public void deleteUserTest() {

        String userId = "userIdabc";

        Mockito.when(userRepository.findById("userIdabc")).thenReturn(Optional.of(user));

        userService.deleteUser(userId);
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }

    @Test
    public void findUserByIdTest() {
        String userId = "userIdabc";

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUserById(userId);

        Assertions.assertNotNull(userDto);

        Assertions.assertEquals(user.getName(), userDto.getName());
    }

    @Test
    public void findUserByEmailTest() {
        String emailId = "userIdabc@gmail.com";

        Mockito.when(userRepository.findByEmail(emailId)).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUserByEmail(emailId);

        Assertions.assertNotNull(userDto);

        Assertions.assertEquals(user.getEmail(), userDto.getEmail());
    }

    @Test
    public void searchUserTest(){


        User user1 = User.builder().name("Shubham").email("S@gmail.com").password("abc").gender("Male").about("Software Engineer").imageName("xyz.png").build();
        User user2 = User.builder().name("Ajay").email("a@gmail.com").password("abc").gender("Male").about("Software Engineer").imageName("xyz.png").build();

        String keyword="Shubham";
        List<User> userList = Arrays.asList(user, user1, user2);

        Mockito.when(userRepository.findByNameContaining(keyword)).thenReturn(userList);
        List<UserDto> userDtos = userService.searchUser(keyword);
        Assertions.assertEquals(3,userDtos.size());

    }
}
