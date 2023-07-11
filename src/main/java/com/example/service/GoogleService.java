package com.example.service;

import com.example.persistence.repositories.AuthorityRepository;
import com.example.persistence.repositories.GoogleAuthorityRepository;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public interface GoogleService {

    OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserServiceGoogle(GoogleAuthorityRepository googleAuthorityRepository);

    LogoutSuccessHandler logoutSuccessHandler();

    OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserServiceFacebook(AuthorityRepository authorityRepository);


}

