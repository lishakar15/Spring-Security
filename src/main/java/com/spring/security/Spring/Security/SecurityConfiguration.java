package com.spring.security.Spring.Security;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration  {

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        //http.authorizeHttpRequests(requests -> requests.anyRequest().permitAll());
//        http.authorizeHttpRequests(requests-> requests.requestMatchers("/admin").hasRole("ADMIN"));
//        http.authorizeHttpRequests
//        (request->request.requestMatchers("/user").hasAnyRole("USER","ADMIN"));
//        http.authorizeHttpRequests((request->request.requestMatchers("/home").permitAll()));
                http.httpBasic(withDefaults());
                //http.formLogin(withDefaults());
        return http.build();

    }
    @Bean
    public UserDetailsService userDetailsService()
    {
        UserDetails user1 = User.withUsername("Lisha")
                .password("{noop}Pass@123")
                .roles("ADMIN")
                .build();
        UserDetails user2 = User.withUsername("Jack")
                .password("{noop}Jack@123")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user1,user2); //InMemoryUserDetailsManager is a child of UserDetailsService
    }

}
