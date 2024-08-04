package com.edigest.journalApp.controller;

import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.UserRepository;
import com.edigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userServices;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public void createUser(@RequestBody User user){
        System.out.print("the create User function called");
        userServices.saveNewUser(user);
    }
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        System.out.print("The username from the put function is: "+ username);
        User userInDb=userServices.findByUsername(username);
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userServices.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(@RequestBody User user){

        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        userRepository.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
