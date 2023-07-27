package com.electronic.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ElectronicStoreApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;
    public static void main(String[] args) {



        SpringApplication.run(ElectronicStoreApplication.class, args);

        System.out.println("Electronic Store Project Up And Running...!!");


    }


  @Override
    public void run(String... args) throws Exception {
        System.out.println(this.passwordEncoder.encode("Shubham"));

    }



}
