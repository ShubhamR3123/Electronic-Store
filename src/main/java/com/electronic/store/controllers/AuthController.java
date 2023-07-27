package com.electronic.store.controllers;

import com.electronic.store.dtos.JwtRequest;
import com.electronic.store.dtos.JwtResponse;
import com.electronic.store.dtos.UserDto;
import com.electronic.store.exceptions.BadApiRequest;
import com.electronic.store.security.JwtHelper;
import com.electronic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtHelper helper;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) throws Exception{
        this.daoAuthenticate(request.getEmail(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        JwtResponse response = JwtResponse.builder()

                .jwtToken(token).user(userDto).build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private void daoAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authenticationToken);

        } catch (BadCredentialsException e) {
            throw new BadApiRequest("Invalid Username Or Password ..");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto) {

        UserDto newUser = this.userService.registerNewUser(userDto);

        return new ResponseEntity<UserDto>(newUser, HttpStatus.CREATED);
    }

//    @GetMapping("/current")
//    public ResponseEntity<UserDto> getCurrentUser(Principal principal){
//        String name = principal.getName();
//
//        return new ResponseEntity<>(modelMapper.map(userDetailsService.loadUserByUsername(name)));
//
//    }
}
