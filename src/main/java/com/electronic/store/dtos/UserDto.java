package com.electronic.store.dtos;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;
    @NotNull
    @Size(min = 4, message = "Username Must be Min 4 Chars...!!")
    private String name;
    @Email
    private String email;

    //Minimum eight characters, at least one letter and one number:


    //    @Pattern(regexp ="^[A-Za-z][A-Za-z0-9)d]*")
    @NotEmpty
    @Size(min = 3, max = 10, message = "Min 3 chars and Max 10 Chars..!!")
    private String password;
    private String gender;
    @Size(min = 15, max = 100, message = "Min 10 Chars and Max 100 Chars...!!")
    private String about;
    private String imageName;


}
