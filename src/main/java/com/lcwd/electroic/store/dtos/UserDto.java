package com.lcwd.electroic.store.dtos;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Data
@Builder
public class UserDto {

    private String userId;

    private String name;
    private String email;
    private String password;
    private String gender;
    private String about;
    private String imageName;



}
