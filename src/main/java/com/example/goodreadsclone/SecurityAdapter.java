package com.example.goodreadsclone;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityAdapter extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        This configuration does the following:
        1. Show /user page only when you're logged in with OAuth github
        2. Show every other page without login
         */
        http.authorizeHttpRequests(
                a->a.antMatchers("/user").authenticated()
                        .anyRequest().permitAll()
        ).oauth2Login();
    }
}
