package com.spring.security.Spring.Security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

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
