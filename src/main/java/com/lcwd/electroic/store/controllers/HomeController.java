package com.lcwd.electroic.store.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class HomeController {

@GetMapping("/t1")
    public String testing(){
        return "Welcome To Electronic Store";
    }
}
