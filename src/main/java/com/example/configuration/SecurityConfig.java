package com.example.configuration;
import com.example.configuration.auth.JWTAuthenticationFilter;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity

public class SecurityConfig {

    private final UserService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JWTAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         http
//                .csrf()
//                .disable()
//                .authorizeHttpRequests( )
//                .requestMatchers()
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .sessionManagment()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .authenticatorProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationToken.class);
//         return http.build();
//    }

    // STILL WORKING ON IT

    //old one
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests( auth -> {
                    auth.requestMatchers("/", "/users/register", "/home", "/users/authenticate").permitAll();
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