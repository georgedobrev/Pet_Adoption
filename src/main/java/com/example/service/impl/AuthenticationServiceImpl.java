package com.example.service.impl;

import com.example.configuration.auth.AuthenticationResponse;
import com.example.persistence.binding.UserLoginBindingModel;
import com.example.persistence.binding.UserRegisterBindingModel;
import com.example.persistence.entities.AuthorityEntity;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.entities.UserSecurityEntity;
import com.example.persistence.enums.RoleEnum;
import com.example.persistence.repositories.AuthorityRepository;
import com.example.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final JWTServiceImpl jwtServiceImpl;
    private final AuthenticationManager authenticationManager;

    //double check!
    public AuthenticationResponse register(UserRegisterBindingModel request) {
        UserEntity user = new UserEntity();

        user.setUserFirstName(request.getUserFirstName());
        user.setUserLastName(request.getUserLastName());
        user.setUserEmail(request.getUserEmail());
        user.setUserPassword(passwordEncoder.encode(request.getUserPassword()));
        // Set role on the User object.
        AuthorityEntity authority = authorityRepository.findByAuthority(RoleEnum.USER);
        if (authority == null) {
            // if the authority does not exist, create a new one
            authority = new AuthorityEntity();
            authority.setAuthority(RoleEnum.USER);
            authority = authorityRepository.save(authority);
        }
        user.getAuthorities().add(authority);
        // Save the User object
        UserEntity savedUser = userRepository.save(user);

        // Wrap the UserEntity in a UserSecurityEntity
        UserSecurityEntity userSecurity = new UserSecurityEntity(savedUser);
        //user->userSecurity;
        var jwtToken = jwtServiceImpl.generateToken(userSecurity);
        var refreshToken = jwtServiceImpl.generateRefreshToken(userSecurity);

        // Save user token
        saveUserToken(savedUser, jwtToken);

        // Create AuthenticationResponse and set properties
        AuthenticationResponse response = new AuthenticationResponse();
        response.setUserAccessToken(jwtToken);
        response.setUserRefreshToken(refreshToken);

        return response;
    }


    //double check!
    public AuthenticationResponse authenticate(UserLoginBindingModel request) {
        // Here we will use the returned Authentication object
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserEmail(),
                        request.getUserPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            UserEntity user = userRepository.findByEmail(request.getUserEmail()).orElseThrow();

            UserEntity savedUser = userRepository.save(user);

            // Wrap the UserEntity in a UserSecurityEntity
            UserSecurityEntity userSecurity = new UserSecurityEntity(savedUser);

            // Generate the tokens
            String jwtToken = jwtServiceImpl.generateToken(userSecurity);
            String refreshToken = jwtServiceImpl.generateRefreshToken(userSecurity);

            // Revoke old tokens and save the new one
            revokeAllUserTokens(userSecurity);
            saveUserToken(userSecurity, jwtToken);

            // Create and return the response
            AuthenticationResponse response = new AuthenticationResponse();
            response.setUserAccessToken(jwtToken);
            response.setUserRefreshToken(refreshToken);

            return response;
        } else {
            throw new BadCredentialsException("Invalid email or password");// The authentication failed.
        }
    }



    //What is this?? Double check!
//    public AuthenticationResponse createAuthenticationResponse(UserEntity user) {
//        AuthenticationResponse response = new AuthenticationResponse();
//        response.setUserAccessToken(user.getUserAccessToken());
//        response.setUserRefreshToken(user.getUserRefreshToken());
//        return response;
//    }
}
