package com.gabriel.tudosimples.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Passwordencoder{

    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
