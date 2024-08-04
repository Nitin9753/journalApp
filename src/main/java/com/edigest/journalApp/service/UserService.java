package com.edigest.journalApp.service;


import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER"));
        userRepository.save(user);
    }
    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(List.of("USER", "ADMIN"));
        userRepository.save(user);
    }

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public void saveUser(User user){

        userRepository.save(user);
    }


    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Object id){
        return userRepository.findById((ObjectId) id);
    }
    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUsername(String userName){
        return userRepository.findByUsername(userName);
    }


}
