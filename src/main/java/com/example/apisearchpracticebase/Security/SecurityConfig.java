package com.example.apisearchpracticebase.Security;

import com.example.apisearchpracticebase.Security.JWTConfigurer;
import com.example.apisearchpracticebase.Security.JWTProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig{
    private final JWTProvider jwtProvider;

    public SecurityConfig(JWTProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers("/server/**").permitAll()
                .requestMatchers("/authentication/**").permitAll()
                .requestMatchers("/firebase_files/**").permitAll()
                .requestMatchers("/apiLogs/**").permitAll()
                .requestMatchers("/student/**").hasRole("STUDENT")
                .requestMatchers("/student/**").hasRole("PRACTICEMANAGER")
                .requestMatchers("/images/**").hasRole("STUDENT")
                .requestMatchers("/images/**").hasRole("PRACTICEMANAGER")
                .requestMatchers("/requests/**").hasRole("STUDENT")
                .requestMatchers("/requests/**").hasRole("PRACTICEMANAGER")
                .requestMatchers("/visitLog/**").hasRole("STUDENT")
                .requestMatchers("/visitLog/**").hasRole("PRACTICEMANAGER")
                .requestMatchers("/practice_Manager/**").hasRole("PRACTICEMANAGER")
                .requestMatchers("/practiceBase/**").hasRole("PRACTICEMANAGER")
                .requestMatchers("/students-resume/**").hasRole("PRACTICEMANAGER")
                .anyRequest().authenticated()
                .and()
                .apply(new JWTConfigurer(jwtProvider));
        return http.build();
    }
}
