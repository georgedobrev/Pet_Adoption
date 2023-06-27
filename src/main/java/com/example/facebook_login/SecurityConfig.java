package com.example.facebook_login;

import com.example.persistence.entities.LoginProviderEntity;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.repositories.LoginProviderRepository;
import com.example.persistence.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.SecurityFilterChain;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;
    private final LoginProviderRepository loginProviderRepository;

    public SecurityConfig(UserRepository userRepository, LoginProviderRepository loginProviderRepository) {
        this.userRepository = userRepository;
        this.loginProviderRepository = loginProviderRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .oauth2Login((oauth2Login) -> oauth2Login
                        .defaultSuccessUrl("/",true)
                        .userInfoEndpoint((userInfo) -> userInfo
                                .userAuthoritiesMapper(grantedAuthoritiesMapper())
                        )
                );
        return http.build();
    }



    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserService() {
        final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

        return userRequest -> {
            OAuth2User user = delegate.loadUser(userRequest);

            String fullName = user.getAttribute("name");
            String email = user.getAttribute("email");
            String accessToken = userRequest.getAccessToken().getTokenValue();
            System.out.println(user.getAttributes());
            //System.out.println(accessToken);
            String[] names = fullName.split(" ", 2);
            String firstName = names[0];
            String lastName = names.length > 1 ? names[1] : ""; //handle case where there is no last name

            UserEntity newUser = null;
            if (!userRepository.existsByUserEmail(email)) {
                newUser = new UserEntity();
                newUser.setUserEmail(email);
                newUser.setUserFirstName(firstName);
                newUser.setUserLastName(lastName);
                newUser.setUserAccessToken(accessToken);
                userRepository.save(newUser);
                LoginProviderEntity newLoginProvider = new LoginProviderEntity();
                newLoginProvider.setUserId(newUser);
                newLoginProvider.setProviderName(userRequest.getClientRegistration().getRegistrationId());
                newLoginProvider.setAccessToken(accessToken);
                loginProviderRepository.save(newLoginProvider);
            } else {
                newUser = userRepository.findByUserEmail(email);
            }



            return user;
        };
    }



    private GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            authorities.forEach((authority) -> {
                GrantedAuthority mappedAuthority;
                if (authority instanceof OidcUserAuthority) {
                    OidcUserAuthority userAuthority = (OidcUserAuthority) authority;
                    mappedAuthority = new OidcUserAuthority(
                            "ROLE_USER", userAuthority.getIdToken(), userAuthority.getUserInfo());
                } else if (authority instanceof OAuth2UserAuthority) {
                    OAuth2UserAuthority userAuthority = (OAuth2UserAuthority) authority;
                    mappedAuthority = new OAuth2UserAuthority(
                            "ROLE_USER", userAuthority.getAttributes());
                } else {
                    mappedAuthority = authority;
                }
                mappedAuthorities.add(mappedAuthority);
            });
            return mappedAuthorities;
        };
    }
}