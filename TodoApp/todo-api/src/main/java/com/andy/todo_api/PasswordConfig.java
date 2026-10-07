package com.andy.todo_api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// Spring register both PasswordConfig and PasswordEncoder as beans.
// PasswordEncoder will be used in the other beans  
// PasswordConfig defines the encoder → Spring creates it → Spring passes the encoder to AuthService.
@Configuration 
public class PasswordConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
