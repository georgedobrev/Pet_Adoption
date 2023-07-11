package com.example.service;

import com.example.persistence.repositories.AuthorityRepository;
import com.example.persistence.repositories.GoogleAuthorityRepository;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public interface GoogleService {

    OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserService(GoogleAuthorityRepository googleAuthorityRepository);



   OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserServiceFaceboook(AuthorityRepository authorityRepository);


}
