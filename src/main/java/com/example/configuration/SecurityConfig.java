package com.example.configuration;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userDetailsService;

    private final PasswordEncoder passwordEncoder;




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests( auth -> {
                    auth.requestMatchers("/", "/users/register", "/home", "/users/login").permitAll();
                    auth.anyRequest().authenticated();
                })

                .formLogin(formLoginConfigurer -> formLoginConfigurer
                        .loginPage("/users/login")
                        .passwordParameter("userPassword").usernameParameter("userEmail")
                        .failureUrl("/users/login?error=true")
                        .permitAll())
                .logout(LogoutConfigurer::permitAll)
                .build();
    }
}