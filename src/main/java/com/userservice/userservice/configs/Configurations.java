package com.userservice.userservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Configurations {
    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoderObject(){
        return new BCryptPasswordEncoder();
    }
}
