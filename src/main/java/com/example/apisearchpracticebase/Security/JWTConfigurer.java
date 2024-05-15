package com.example.apisearchpracticebase.Security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private JWTProvider jwtProvider;

    public JWTConfigurer(JWTProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public void configure(HttpSecurity security) throws Exception{
        JWTFilter jwtFilter = new JWTFilter(jwtProvider);
        security.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
