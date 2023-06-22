package com.example.services;

import com.example.persistence.entities.LoginProviderEntity;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.repositories.LoginProviderRepository;
import com.example.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginProviderRepository loginProviderRepository;

    public UserEntity saveUserFromOAuth2(OAuth2AuthenticationToken authentication) {
        String email = authentication.getPrincipal().getAttribute("email");
        UserEntity user = userRepository.findByUserEmail(email);

        if (user == null) {
            user = new UserEntity();
            user.setUserEmail(email);
            user.setUserFirstName(authentication.getPrincipal().getAttribute("name"));
            user.setUserAccessToken(authentication.getPrincipal().getAttribute("access_token"));
            user.setUserRefreshToken(authentication.getPrincipal().getAttribute("refresh_token"));
            userRepository.save(user);
        }

        LoginProviderEntity loginProvider = new LoginProviderEntity();
        loginProvider.setUserId(user);
        loginProvider.setProviderName(authentication.getAuthorizedClientRegistrationId());
        loginProvider.setAccessToken(authentication.getPrincipal().getAttribute("access_token"));

        loginProviderRepository.save(loginProvider);

        return user;
    }
}
