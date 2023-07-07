package com.example.configuration;
import com.example.configuration.auth.JWTAuthenticationFilter;
import com.example.persistence.entities.LoginProviderEntity;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.enums.RoleEnum;
import com.example.persistence.repositories.GoogleAuthorityRepository;
import com.example.persistence.repositories.LoginProviderRepository;
import com.example.persistence.repositories.UserRepository;
import com.example.service.GoogleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity

public class SecurityConfig {
//extends SecurityConfigureAdapter
    private final JWTAuthenticationFilter jwtAuthFilter;
    private  final UserRepository userRepository;
    private final LoginProviderRepository loginProviderRepository;
    private final GoogleService googleService;
    //private final UserService userDetailsService;
    //private final PasswordEncoder passwordEncoder;
    //private final AuthenticationProvider authenticationProvider;

    //old one
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/users/register", "/home", "/users/login").permitAll()
                        //.requestMatchers("/user/**").hasRole("USER")
                        //.requestMatchers("/index/update-user").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(fl -> fl
                        .loginPage("/users/login")
                        .usernameParameter("userEmail")
                        .passwordParameter("userPassword")
                        .failureUrl("/users/login?error=true")
                        .permitAll()
                )
                .oauth2Login((oauth2Login) -> oauth2Login
                        .defaultSuccessUrl("/", true)
                        .userInfoEndpoint((userInfo) -> userInfo
                                .userAuthoritiesMapper(googleService.grantedAuthoritiesMapper())
                        )
                )
                .logout(l -> l
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .build();
    }


}