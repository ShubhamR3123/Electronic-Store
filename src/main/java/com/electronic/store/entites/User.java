package com.electronic.store.entites;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends CustomFields{


   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private String userId;
    @Column(name = "user_name", nullable = false)
    private String name;
    @Column(name = "user_email", unique = true)
    private String email;
    @Column(name = "user_password", length = 10, nullable = false)
    private String password;

    @Column(nullable = false)
    private String gender;
    //@Column(length = 100)
    private String about;
    @Column(name = "user_image_name")
    private String imageName;

}
