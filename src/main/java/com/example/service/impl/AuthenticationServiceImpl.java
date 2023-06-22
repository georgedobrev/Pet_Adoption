package com.example.service.impl;

import com.example.configuration.auth.AuthenticationResponse;
import com.example.persistence.binding.UserLoginBindingModel;
import com.example.persistence.binding.UserRegisterBindingModel;
import com.example.persistence.entities.AuthorityEntity;
import com.example.persistence.entities.TokenEntity;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.entities.UserSecurityEntity;
import com.example.persistence.enums.RoleEnum;
import com.example.persistence.enums.TokenTypeEnum;
import com.example.persistence.repositories.AuthorityRepository;
import com.example.persistence.repositories.TokenRepository;
import com.example.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final JWTServiceImpl jwtServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

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

        // Create UserDetails from UserEntity
        UserDetails userDetails = User.withUsername(savedUser.getUserEmail())
                .password(savedUser.getUserPassword())
                .authorities((GrantedAuthority) savedUser.getAuthorities().stream()
                        .map(AuthorityEntity::getAuthority).collect(Collectors.toList()))
                .build();        //user->userSecurity;
        var jwtToken = jwtServiceImpl.generateToken(userDetails);
        var refreshToken = jwtServiceImpl.generateRefreshToken(userDetails);

        // Save user token
        saveUserToken(savedUser, jwtToken);

        // Create AuthenticationResponse and set properties
        AuthenticationResponse response = new AuthenticationResponse();
        response.setUserAccessToken(jwtToken);
        response.setUserRefreshToken(refreshToken);

        return response;
    }

//-------------------------------------------------------------------------------
    //double check!
//    public AuthenticationResponse authenticate(UserLoginBindingModel request) {
//        // Here we will use the returned Authentication object
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        request.getUserEmail(),
//                        request.getUserPassword()
//                )
//        );
//        if (authentication.isAuthenticated()) {
//            UserEntity user = userRepository.findByEmail(request.getUserEmail()).orElseThrow();
//
//            UserEntity savedUser = userRepository.save(user);
//
//            // Wrap the UserEntity in a UserSecurityEntity
//            UserSecurityEntity userSecurity = new UserSecurityEntity(savedUser);
//
//            // Generate the tokens
//            String jwtToken = jwtServiceImpl.generateToken(userSecurity);
//            String refreshToken = jwtServiceImpl.generateRefreshToken(userSecurity);
//
//            // Revoke old tokens and save the new one
//            revokeAllUserTokens(userSecurity);
//            saveUserToken(userSecurity, jwtToken);
//
//            // Create and return the response
//            AuthenticationResponse response = new AuthenticationResponse();
//            response.setUserAccessToken(jwtToken);
//            response.setUserRefreshToken(refreshToken);
//
//            return response;
//        } else {
//            throw new BadCredentialsException("Invalid email or password");// The authentication failed.
//        }
//    }
//--------------------------------------------------------------------------------------------------------

    public AuthenticationResponse authenticate(UserLoginBindingModel request) {
        // Here we will use the returned Authentication object
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUserEmail(),
                        request.getUserPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            // Load the UserEntity using the email
            UserEntity userEntity = userRepository.findByEmail(request.getUserEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            // Create UserDetails from UserEntity
            UserDetails userDetails = User.withUsername(userEntity.getUserEmail())
                    .password(userEntity.getUserPassword())
                    .authorities((GrantedAuthority) userEntity.getAuthorities().stream()
                            .map(AuthorityEntity::getAuthority).collect(Collectors.toList()))
                    .build();

            // Generate the tokens using UserDetails
            String jwtToken = jwtServiceImpl.generateToken(userDetails);
            String refreshToken = jwtServiceImpl.generateRefreshToken(userDetails);

            // Revoke old tokens and save the new one
            revokeAllUserTokens(userEntity);
            saveUserToken(userEntity, jwtToken);

            // Create and return the response
            AuthenticationResponse response = new AuthenticationResponse();
            response.setUserAccessToken(jwtToken);
            response.setUserRefreshToken(refreshToken);

            return response;
        } else {
            // The authentication failed. Throw an exception or return an error response as appropriate.
            throw new BadCredentialsException("Invalid username or password");
        }
    }



    private void saveUserToken(UserEntity userEntityToken, String jwtToken) {
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

    private void revokeAllUserTokens(UserEntity userEntity) {
        // Assuming you have a method in your TokenRepository or service layer to get all valid tokens for a user
        List<TokenEntity> validUserTokens = tokenRepository.findAllValidTokenByUser(userEntity.getUserID());

        if (validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

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
                UserEntity user = this.userRepository.findByEmail(userEmail)
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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(username);
        if (userEntityOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        UserEntity userEntity = userEntityOptional.get();
        return new User(userEntity.getUserEmail(), userEntity.getUserPassword(), new ArrayList<>());
    }

}
