package com.lcwd.electroic.store.entites;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {


    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private String userId;
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_email", unique = true)
    private String email;
    @Column(name = "user_password", length = 10)
    private String password;
    @Column(name = "user_gender")
    private String gender;
    @Column(length = 10)
    private String about;
    @Column(name = "user_image_name")
    private String imageName;

}
