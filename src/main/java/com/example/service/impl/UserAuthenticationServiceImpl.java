package com.example.service.impl;

import com.example.configuration.auth.AuthenticationResponse;
import com.example.persistence.binding.UserLoginBindingModel;
import com.example.persistence.binding.UserRegisterBindingModel;
import com.example.persistence.entities.TokenEntity;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.enums.RoleEnum;
import com.example.persistence.enums.TokenTypeEnum;
import com.example.persistence.repositories.AuthorityRepository;
import com.example.persistence.repositories.TokenRepository;
import com.example.persistence.repositories.UserRepository;
import com.example.service.UserAuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final JWTServiceImpl jwtServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final AuthorityServiceImpl authorityServiceImpl;
    //double check!
    @Transactional
    public AuthenticationResponse register(UserRegisterBindingModel request) {
        UserEntity newUser = new UserEntity();

        newUser.setUserFirstName(request.getUserFirstName());
        newUser.setUserLastName(request.getUserLastName());
        newUser.setUserEmail(request.getUserEmail());
        //add default picture
        //newUser.setUserPhotoURL("https://cdn.vox-cdn.com/thumbor/qds1ovjTYIqLY6Cr2jW1YfeDJ-s=/1400x1400/filters:format(jpeg)/cdn.vox-cdn.com/uploads/chorus_asset/file/21730397/avatar_airbender.jpg");
        if (userRepository.count() == 0) {
            authorityServiceImpl.seedAuthorities();
            newUser.setAuthorities(new HashSet<>(authorityRepository.findAll()));
        } else {
            newUser.setAuthorities(new HashSet<>(authorityRepository.findAllByAuthority(RoleEnum.USER)));
        }
        newUser.setUserPassword(passwordEncoder.encode(request.getUserPassword()));

        // Save the User object
        UserEntity savedUser = userRepository.save(newUser);

        // Create UserDetails from UserEntity
        UserDetails userDetails = toUserDetails(savedUser);      //newUser->userSecurity;
        var jwtToken = jwtServiceImpl.generateToken(userDetails);
        var refreshToken = jwtServiceImpl.generateRefreshToken(userDetails);
        // Save newUser token
        saveUserToken(savedUser, jwtToken);
        // Create AuthenticationResponse and set properties
        AuthenticationResponse response = new AuthenticationResponse();
        response.setUserAccessToken(jwtToken);
        response.setUserRefreshToken(refreshToken);
        return response;
    }

    @Transactional
    public AuthenticationResponse authenticate(UserLoginBindingModel request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserEmail(),
                        request.getUserPassword())
        );
        UserEntity userEntity = userRepository.findByUserEmail(request.getUserEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        UserDetails userDetails = toUserDetails(userEntity);

        String jwtToken = jwtServiceImpl.generateToken(userDetails);
        String refreshToken = jwtServiceImpl.generateRefreshToken(userDetails);

        revokeAllUserTokens(userEntity);
        saveUserToken(userEntity, jwtToken);

        //instead of build pattern
        AuthenticationResponse response = new AuthenticationResponse();
        response.setUserAccessToken(jwtToken);
        response.setUserRefreshToken(refreshToken);

        return response;
    }

    @Transactional
    public void saveUserToken(UserEntity userEntityToken, String jwtToken) {
        // Create a new TokenEntity object
        TokenEntity token = new TokenEntity();
        // Set the properties of the token
        token.setUserEntity(userEntityToken);
        token.setToken(jwtToken);
        token.setTokenType(TokenTypeEnum.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        // Save the token
        tokenRepository.save(token);
    }

    @Transactional
    public void revokeAllUserTokens(UserEntity userEntity) {
        List<TokenEntity> validUserTokens = tokenRepository.findAllValidTokenByUser(userEntity.getUserID());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
            tokenRepository.save(token);
        });
//        validUserTokens.forEach(token -> {
//            token.setExpired(true);
//            token.setRevoked(true);
//        });
//        tokenRepository.saveAll(validUserTokens);
    }

    @Transactional
    public AuthenticationResponse refreshToken(String refreshToken) throws IOException {
        // Extract the user email from the token
        String userEmail = jwtServiceImpl.extractUsername(refreshToken);
        // If the userEmail is not null, proceed
        if (userEmail != null) {
            // Load the user details
            UserDetails userDetails = loadUserByUsername(userEmail);
            // Check if the token is valid
            if (jwtServiceImpl.isTokenValid(refreshToken, userDetails)) {
                // Generate a new access token
                String accessToken = jwtServiceImpl.generateToken(userDetails);
                // Find the user entity
                UserEntity user = this.userRepository.findByUserEmail(userEmail)
                        .orElseThrow(); // Throw an exception if the user doesn't exist
                // Revoke all the user's tokens
                revokeAllUserTokens(user);
                // Save the new access token
                saveUserToken(user, accessToken);
                // Create a new authentication response with the new access token and the refresh token
                AuthenticationResponse authResponse = new AuthenticationResponse();
                authResponse.setUserAccessToken(accessToken);
                authResponse.setUserRefreshToken(refreshToken);
                // Return the authentication response
                return authResponse;
            }
        }
        // If the userEmail is null or the token is not valid, return null
        return null;
    }

    public UserDetails toUserDetails(UserEntity userEntity) {
        return User.withUsername(userEntity.getUserEmail())
                .password(userEntity.getUserPassword())
                .authorities(userEntity.getAuthorities().stream()
                        .map(authorityEntity -> new SimpleGrantedAuthority(authorityEntity.getAuthority()))
                        .collect(Collectors.toList()))
                .build();
    }
    @Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByUserEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    return toUserDetails(userEntity);
}

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean emailExists(String userEmail) {
            return userRepository.findUserByUserEmail(userEmail) != null;
    }

}
