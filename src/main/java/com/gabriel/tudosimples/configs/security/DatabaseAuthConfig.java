package com.gabriel.tudosimples.configs.security;

import com.gabriel.tudosimples.integration.login.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DatabaseAuthConfig {

    @Bean
    public AuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder, AuthService authService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(authService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}