package com.security.TodoList.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration

public class webSecurityConfig  {


    private static final String[] WHITE_LIST_LABEL ={
            "/hello",
            "/register*",
            "/verifyRegistration",
            "/resendToken",
            "/restPassword",
            "/savePassword",
            "/changePassword"

    };
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder (11);

    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors ()
                .and ()
                .csrf ()
                .disable ()
                .authorizeHttpRequests ()
                .requestMatchers ( WHITE_LIST_LABEL )
                .permitAll ();
        return http.build ();

    }


}
