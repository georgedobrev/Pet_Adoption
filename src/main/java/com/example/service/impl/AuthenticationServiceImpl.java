package com.example.service.impl;

import com.example.configuration.auth.AuthenticationResponse;
import com.example.persistence.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {
    public AuthenticationResponse createAuthenticationResponse(UserEntity user) {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setUserAccessToken(user.getUserAccessToken());
        response.setUserRefreshToken(user.getUserRefreshToken());
        return response;
    }
}
