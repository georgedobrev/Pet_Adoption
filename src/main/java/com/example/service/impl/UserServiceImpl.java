package com.example.service.impl;

import com.example.exceptions.UserNotFoundException;
import com.example.configuration.auth.AuthenticationResponse;
import com.example.persistence.binding.UserLoginBindingModel;
import com.example.persistence.binding.UserRegisterBindingModel;
import com.example.persistence.entities.LoginProviderEntity;
import com.example.persistence.entities.TokenEntity;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.entities.UserSecurityEntity;
import com.example.persistence.enums.RoleEnum;
import com.example.persistence.enums.TokenTypeEnum;
import com.example.persistence.repositories.AuthorityRepository;
import com.example.persistence.repositories.LoginProviderRepository;
import com.example.persistence.repositories.TokenRepository;
import com.example.persistence.repositories.UserRepository;
import com.example.service.UserService;
import com.example.mapper.UserRegisterMapper;
import com.example.persistence.binding.UserRegisterBindingModel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private JavaMailSender mailSender;
    private final AuthorityRepository authorityRepository;
    private final JWTServiceImpl jwtServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final AuthorityServiceImpl authorityServiceImpl;
    private final LoginProviderRepository loginProviderRepository;
    //double check!
    @Transactional
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
        newLoginProvider.setProviderName("Local");
        newLoginProvider.setAccessToken("JWTToken!?"); //implement jwt token
        loginProviderRepository.save(newLoginProvider);

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
        saveUserToken(savedUser, jwtToken, refreshToken);
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
        saveUserToken(userEntity, jwtToken, refreshToken);

        //instead of build pattern
        AuthenticationResponse response = new AuthenticationResponse();
        response.setUserAccessToken(jwtToken);
        response.setUserRefreshToken(refreshToken);

        return response;
    }

    @Transactional
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
                saveUserToken(user, accessToken, refreshToken);
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


    //new beta Version
    public UserDetails toUserDetails(UserEntity userEntity) {
        return new UserSecurityEntity(userEntity);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserSecurityEntity(userEntity);
    }
    //doubleCheck
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        UserEntity user = userRepository.findByUserEmail(email);
//        if (user == null) {
//            throw new UsernameNotFoundException(email);
//        }
//        return new User(user.getUserEmail(), user.getUserPassword(), Collections.singleton(new SimpleGrantedAuthority(user.getRoles().toString())));
//    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }



        public boolean emailExists(String userEmail){
            return userRepository.findUserByUserEmail(userEmail) != null;
        }


        @Override
        public void updateResetPasswordToken (String token, String email) throws UserNotFoundException {
            UserEntity user = userRepository.findUserByUserEmail(email);
            if (user != null) {
                user.setUserResetPasswordToken(token);
                userRepository.save(user);
            } else {
                throw new UserNotFoundException("Could not find any user with the email " + email);
            }
        }

        @Override
        public UserEntity getByResetPasswordToken (String token){
            return userRepository.findByUserResetPasswordToken(token);
        }

        @Override
        public void updatePassword (UserEntity user, String newPassword){
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setUserPassword(encodedPassword);

            user.setUserResetPasswordToken(null);
            userRepository.save(user);
        }

        @Override
        public void registerEmailSender (UserEntity user, String siteURL) throws UnsupportedEncodingException, MessagingException {
            String encodedPassword = passwordEncoder.encode(user.getUserPassword());
            user.setUserPassword(encodedPassword);

            String randomCode = RandomString.make(64);
            user.setVerificationCode(randomCode);
            user.setEnabled(false);

            userRepository.save(user);

            sendVerificationEmail(user, siteURL);
        }

        @Override
        public void sendVerificationEmail (UserEntity user, String siteURL) throws
        MessagingException, UnsupportedEncodingException {
            String toAddress = user.getUserEmail();
            String fromAddress = "pawfinder.team@gmail.com";
            String senderName = "Paw Finder";
            String subject = "Please verify your registration";
            String content = "Dear [[name]],<br>"
                    + "Please click the link below to verify your registration:<br>"
                    + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                    + "Thank you,<br>"
                    + "Paw Finder";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);

            String fullName = user.getUserFirstName() + " " + user.getUserLastName();
            content = content.replace("[[name]]", fullName);
            String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

            content = content.replace("[[URL]]", verifyURL);

            helper.setText(content, true);

            mailSender.send(message);
        }

        @Override
        public boolean verify (String verificationCode){
            UserEntity user = userRepository.findByVerificationCode(verificationCode);

            if (user == null || user.isEnabled()) {
                return false;
            } else {
                user.setVerificationCode(null);
                user.setEnabled(true);
                userRepository.save(user);

                return true;
            }

        }
    }
