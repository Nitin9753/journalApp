package com.edigest.journalApp.service;

import com.edigest.journalApp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        com.edigest.journalApp.entity.User getUser=userRepository.findByUsername(username);
        if(getUser==null) throw new UsernameNotFoundException("User not found with username: "+ username);
        return User.builder()
                .username(getUser.getUsername())
                .password(getUser.getPassword())
                .roles(getUser.getRoles().toArray(new String[0]))
                .build();
    }
}
