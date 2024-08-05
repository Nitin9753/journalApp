package com.edigest.journalApp.controller;

import com.edigest.journalApp.cache.AppCache;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private AppCache appCache;
    @GetMapping("/all-users")
    public ResponseEntity<?> getAll(){
        List<User> all=userService.getAll();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody User user){
        userService.saveAdmin(user);
    }

    @GetMapping("/clear-app-cache")
    public ResponseEntity<?> clearAppCache(){
        try{
            appCache.init();
            return new ResponseEntity<>("App cache is cleared", HttpStatus.OK);
        }catch (Exception e){
            System.out.print("error in clear-app-cache of the admin controller file"+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
