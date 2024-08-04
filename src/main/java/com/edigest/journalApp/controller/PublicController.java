package com.edigest.journalApp.controller;

import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")

public class PublicController {

    @Autowired
    private UserService userServices;
    @GetMapping
    public String health(){
        return "OK";
    }

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Ok";
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        System.out.print("the create User function called");
        userServices.saveNewUser(user);
    }
}
