package com.electronic.store.controllers;

import com.electronic.store.dtos.CategoryDto;
import com.electronic.store.dtos.PageableResponse;
import com.electronic.store.dtos.UserDto;
import com.electronic.store.entites.User;
import com.electronic.store.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    private User user;

    @BeforeEach
    public void init() {
        user = User.builder().name("Shubham").email("s@gmail.com").password("abc").gender("Male").about("Software Engineer").imageName("xyz.png").build();

    }

    @Test
    public void createUserTest() throws Exception {
        //users+Post+user data as a json
        //data as json+created

        UserDto dto = modelMapper.map(user, UserDto.class);
        Mockito.when(userService.craeteUser(Mockito.any())).thenReturn(dto);

        //actual request for url

        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/").contentType(MediaType.APPLICATION_JSON).content(ConvertObjectToJsonString(user)).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.name").exists());
    }


    @Test
    public void updateUserTset() throws Exception {

        String userId = "1234";

        UserDto dto = this.modelMapper.map(user, UserDto.class);
        Mockito.when(userService.updateUser(Mockito.any(), Mockito.anyString())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/users/" + userId).contentType(MediaType.APPLICATION_JSON).content(ConvertObjectToJsonString(user)).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void getAllUsersTest() throws Exception {

        UserDto object1 = UserDto.builder().name("Shubham").email("s@gmail.com").gender("Male").password("abc").about("Testing").build();
        UserDto object2 = UserDto.builder().name("Ajay").email("a@gmail.com").gender("Male").password("abc").about("Developer").build();
        UserDto object3 = UserDto.builder().name("Aditi").email("ad@gmail.com").gender("Female").password("abc").about("Tester").build();


        PageableResponse<UserDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList());
        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(100);
        pageableResponse.setTotalElements(1000);
        Mockito.when(userService.getAllUsers(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

    }



    @Test
    public void deleteUserTest() throws Exception {

        String userId = "12345";

        UserDto dto = this.modelMapper.map(user, UserDto.class);
        Mockito.doNothing().when(userService).deleteUser(userId);
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/users/" + userId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
    }
    @Test
    public void getUserByIdTest() throws Exception {

        String userId = "1234";

        UserDto dto = this.modelMapper.map(user, UserDto.class);
        Mockito.when(userService.getUserById(Mockito.any())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/" + userId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void getUserByEmailTest() throws Exception {

        String  email = "s@gmail.com";

        UserDto dto = this.modelMapper.map(user, UserDto.class);
        Mockito.when(userService.getUserByEmail(Mockito.any())).thenReturn(dto);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/email/" + email).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

    }
    @Test
    public void searchUserTest() throws Exception {

        UserDto user1 = UserDto.builder().name("Shubham").email("S@gmail.com").password("abc").gender("Male").about("Software Engineer").imageName("xyz.png").build();
        UserDto user2 = UserDto.builder().name("Ajay").email("a@gmail.com").password("abc").gender("Male").about("Software Engineer").imageName("xyz.png").build();
        String keyword="Shubham";

        Mockito.when(userService.searchUser(Mockito.anyString())).thenReturn(Arrays.asList(user1,user2));

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/users/search/"+keyword)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk());
    }



    private String ConvertObjectToJsonString(Object user) {
        try {

            return new ObjectMapper().writeValueAsString(user);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
