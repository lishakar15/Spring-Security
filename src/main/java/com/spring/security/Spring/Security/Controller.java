package com.spring.security.Spring.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    UserRepository userRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminPage()
    {
        return "Hello Admin";
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/user")
    public String userPage()
    {
        return "Hello User";
    }
    @PostMapping("/save-user")
    public ResponseEntity<Users> saveUser(@RequestBody Users users)
    {
        String rawPassword = users.getPassword();
        users.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));
        Users savedUsers = userRepository.save(users);
        return new ResponseEntity<>(savedUsers, HttpStatus.OK);
    }

    @GetMapping("/home")
    public String homePage()
    {
        return "Home Page";
    }
    @GetMapping("/products")
    public String getProducts()
    {
        return "Product List";
    }

}
