package com.spring.security.Spring.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Users users = userRepository.findByUserName(username);
        UserDetails userDetails = null;
        if(users != null)
       {
            userDetails = User.withUsername(users.getUserName())
                    .password(users.getPassword())
                    .roles(users.getRole())
                    .build();
       }
        if(userDetails == null)
        {
            System.out.println("User Doesn't exist");
            throw new UsernameNotFoundException("User Doesn't exist");
        }
        return userDetails;
    }
}
