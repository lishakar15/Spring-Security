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
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.beans.Encoder;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfiguration  {
    @Autowired
    DataSource dataSource;

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        //http.authorizeHttpRequests(requests -> requests.anyRequest().permitAll());
       /* http.authorizeHttpRequests(requests-> requests.requestMatchers("/admin").hasRole("ADMIN"));
        http.authorizeHttpRequests(request->request.requestMatchers("/user").hasAnyRole("USER","ADMIN"));*/
        http.authorizeHttpRequests((request->request.requestMatchers("/home").permitAll()));
        http.authorizeHttpRequests(requests -> requests.requestMatchers("/h2-console/**").permitAll());
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        http.csrf(csrf -> csrf.disable()); // This is only for the H2 Database - Testing purpose only
        http.authorizeHttpRequests(requests -> requests.anyRequest().authenticated());

        http.httpBasic(withDefaults());
        //http.formLogin(withDefaults());
        return http.build();

    }
    @Bean
    public UserDetailsService userDetailsService()
    {
        UserDetails user1 = User.withUsername("Lisha")
                .password(passwordEncoder().encode("Lisha@123"))
                .roles("ADMIN")
                .build();
        UserDetails user2 = User.withUsername("Jack")
                .password(passwordEncoder().encode("Jack@123"))
                .roles("USER")
                .build();
        //For H2 database UserDetailsService
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.createUser(user1);
        userDetailsManager.createUser(user2);
        return userDetailsManager;
        //return new InMemoryUserDetailsManager(user1,user2); //InMemoryUserDetailsManager is a child of
        // UserDetailsService
    }
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
