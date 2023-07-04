package com.example.configuration;
import com.example.configuration.auth.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity

public class SecurityConfig {
//extends SecurityConfigureAdapter
    private final JWTAuthenticationFilter jwtAuthFilter;
    //private final UserService userDetailsService;
    //private final PasswordEncoder passwordEncoder;
    //private final AuthenticationProvider authenticationProvider;

    //old one
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/users/register", "/home", "/users/login").permitAll()
                        //.requestMatchers("/user/**").hasRole("USER")
                        //.requestMatchers("/index/update-user").hasRole("ADMIN")

                        .anyRequest().permitAll())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(fl -> fl
                        .loginPage("/users/login")
                        .usernameParameter("userEmail")
                        .passwordParameter("userPassword")
                        .failureUrl("/users/login?error=true")
                        .permitAll())
                .logout(l -> l
                        .logoutUrl("/users/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .build();
    }

}