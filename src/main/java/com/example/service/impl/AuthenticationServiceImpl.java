package com.example.service.impl;

import com.example.configuration.auth.AuthenticationResponse;
import com.example.persistence.binding.UserLoginBindingModel;
import com.example.persistence.binding.UserRegisterBindingModel;
import com.example.persistence.entities.LoginProviderEntity;
import com.example.persistence.entities.TokenEntity;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.enums.RoleEnum;
import com.example.persistence.enums.TokenTypeEnum;
import com.example.persistence.repositories.AuthorityRepository;
import com.example.persistence.repositories.LoginProviderRepository;
import com.example.persistence.repositories.TokenRepository;
import com.example.persistence.repositories.UserRepository;
import com.example.service.AuthenticationService;
import com.example.service.AuthorityService;
import com.example.service.JWTService;
import com.example.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final AuthorityService authorityService;
    private final LoginProviderRepository loginProviderRepository;
    private final UserService userService;

    public AuthenticationResponse register(UserRegisterBindingModel request) {
        UserEntity newUser = new UserEntity();

        newUser.setUserFirstName(request.getUserFirstName());
        newUser.setUserLastName(request.getUserLastName());
        newUser.setUserEmail(request.getUserEmail());
        newUser.setUserPhone(request.getUserPhone());

        // Save the User object first
        newUser = userRepository.save(newUser);

        //Georges stuff
        LoginProviderEntity newLoginProvider = new LoginProviderEntity();
        newLoginProvider.setUserId(newUser);
        newLoginProvider.setProviderName("local");
        newLoginProvider.setAccessToken("JWTToken!?"); //implement jwt token
        loginProviderRepository.save(newLoginProvider);

        //add default picture
        //newUser.setUserPhotoURL("https://cdn.vox-cdn.com/thumbor/qds1ovjTYIqLY6Cr2jW1YfeDJ-s=/1400x1400/filters:format(jpeg)/cdn.vox-cdn.com/uploads/chorus_asset/file/21730397/avatar_airbender.jpg");
        if (userRepository.count() == 0) {
            authorityService.seedAuthorities();
            newUser.setAuthorities(new HashSet<>(authorityRepository.findAll()));
        } else {
            newUser.setAuthorities(new HashSet<>(authorityRepository.findAllByAuthority(RoleEnum.USER)));
        }
        newUser.setUserPassword(passwordEncoder.encode(request.getUserPassword()));

        // Save the User object
        UserEntity savedUser = userRepository.save(newUser);

        // Create UserDetails from UserEntity
        UserDetails userDetails = userService.toUserDetails(savedUser);      //newUser->userSecurity;
        var jwtToken = jwtService.generateToken(userDetails);
        var refreshToken = jwtService.generateRefreshToken(userDetails);
        // Save newUser token
        saveUserToken(savedUser, jwtToken, refreshToken);
        // Create AuthenticationResponse and set properties
        AuthenticationResponse response = new AuthenticationResponse();
        response.setUserAccessToken(jwtToken);
        response.setUserRefreshToken(refreshToken);
        return response;
    }

    @Transactional
    @Override
    public AuthenticationResponse authenticate(UserLoginBindingModel request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserEmail(),
                        request.getUserPassword())
        );
        UserEntity userEntity = userRepository.findByUserEmail(request.getUserEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDetails userDetails = userService.toUserDetails(userEntity);

        String jwtToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        revokeAllUserTokens(userEntity);
        saveUserToken(userEntity, jwtToken, refreshToken);

        //instead of build pattern
        AuthenticationResponse response = new AuthenticationResponse();
        response.setUserAccessToken(jwtToken);
        response.setUserRefreshToken(refreshToken);

        return response;
    }
    @Transactional
    @Override
    public AuthenticationResponse refreshToken(String refreshToken) throws IOException {
        // Extract the user email from the token
        String userEmail = jwtService.extractUsername(refreshToken);
        // If the userEmail is not null, proceed
        if (userEmail != null) {
            // Load the user details
            UserDetails userDetails = userService.loadUserByUsername(userEmail);
            // Check if the token is valid
            if (jwtService.isTokenValid(refreshToken, userDetails)) {
                // Generate a new access token
                String accessToken = jwtService.generateToken(userDetails);
                // Find the user entity
                UserEntity user = this.userRepository.findByUserEmail(userEmail)
                        .orElseThrow(); // Throw an exception if the user doesn't exist
                // Revoke all the user's tokens
                revokeAllUserTokens(user);
                // Save the new access token
                saveUserToken(user, accessToken, refreshToken);
                // Create a new authentication response with the new access token and the refresh token
                AuthenticationResponse authResponse = new AuthenticationResponse();
                authResponse.setUserAccessToken(accessToken);
                authResponse.setUserRefreshToken(refreshToken);
                return authResponse;
            }
        }
        // If the userEmail is null or the token is not valid, return null
        return null;
    }

    @Transactional
    @Override
    public void saveUserToken(UserEntity userEntityToken, String jwtToken, String refreshToken) {
        // Create a new TokenEntity object
        TokenEntity token = new TokenEntity();
        // Set the properties of the token
        token.setUserEntity(userEntityToken);
        token.setToken(jwtToken);
        token.setToken_type(TokenTypeEnum.BEARER);
        token.setRefreshToken(refreshToken);
        token.setExpired(false);
        token.setRevoked(false);
        // Save the token
        tokenRepository.save(token);
    }

    @Transactional
    @Override
    public void revokeAllUserTokens(UserEntity userEntity) {
        List<TokenEntity> validUserTokens = tokenRepository.findAllValidTokenByUser(userEntity.getUserID());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
            tokenRepository.save(token);
        });
    }
}
