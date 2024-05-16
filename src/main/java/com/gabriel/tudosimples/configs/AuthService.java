package com.gabriel.tudosimples.configs;

import com.gabriel.tudosimples.usecases.impl.UserUseCaseImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService{

    private final UserUseCaseImpl userService;

    @Bean
    public UserDetailsService userDetailsService(){
        return userService::findByusername;
    }

}
