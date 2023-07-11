package com.example.service.impl;

import com.example.persistence.entities.LoginProviderEntity;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.enums.RoleEnum;
import com.example.persistence.repositories.AuthorityRepository;
import com.example.persistence.repositories.GoogleAuthorityRepository;
import com.example.persistence.repositories.LoginProviderRepository;
import com.example.persistence.repositories.UserRepository;
import com.example.service.GoogleService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class GoogleServiceImpl implements GoogleService {

    private final UserRepository userRepository;
    private final LoginProviderRepository loginProviderRepository;

    public GoogleServiceImpl(UserRepository userRepository, LoginProviderRepository loginProviderRepository) {
        this.userRepository = userRepository;
        this.loginProviderRepository = loginProviderRepository;
    }

    @Override
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserServiceGoogle(GoogleAuthorityRepository googleAuthorityRepository) {
        final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return userRequest -> {
            OAuth2User user = delegate.loadUser(userRequest);
            String firstName = user.getAttribute("given_name");
            String lastName = user.getAttribute("family_name");
            String photoURL = user.getAttribute("picture");
            String email = user.getAttribute("email");
            String accessToken = userRequest.getAccessToken().getTokenValue();
            System.out.println(user.getAttributes());
            UserEntity newUser = null;
            if (!userRepository.existsByUserEmail(email)) {
                newUser = new UserEntity();
                newUser.setUserEmail(email);
                newUser.setUserFirstName(firstName);
                newUser.setUserLastName(lastName);
                newUser.setUserAccessToken(accessToken);
                newUser.setUserPhotoURL(photoURL);
                userRepository.save(newUser);
                newUser.setAuthorities(new HashSet<>(googleAuthorityRepository.findAllByAuthority(RoleEnum.USER)));
                userRepository.save(newUser);
                LoginProviderEntity newLoginProvider = new LoginProviderEntity();
                newLoginProvider.setUserId(newUser);
                newLoginProvider.setProviderName(userRequest.getClientRegistration().getRegistrationId());
                newLoginProvider.setAccessToken(accessToken);
                loginProviderRepository.save(newLoginProvider);
            } else {
                newUser = userRepository.findUserByUserEmail(email);
            }
            return user;
        };
    }

    @Override
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserServiceFacebook(AuthorityRepository authorityRepository) {
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
                newUser = userRepository.save(newUser);
                newUser.setAuthorities(new HashSet<>(authorityRepository.findAllByAuthority(RoleEnum.USER)));
                userRepository.save(newUser);
                LoginProviderEntity newLoginProvider = new LoginProviderEntity();
                newLoginProvider.setUserId(newUser);
                newLoginProvider.setProviderName(userRequest.getClientRegistration().getRegistrationId());
                newLoginProvider.setAccessToken(accessToken);
                loginProviderRepository.save(newLoginProvider);
            }
            return user;
        };
    }
    @Override
    public LogoutSuccessHandler logoutSuccessHandler() {
        SimpleUrlLogoutSuccessHandler logoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
        logoutSuccessHandler.setUseReferer(true);
        return logoutSuccessHandler;
    }
}
