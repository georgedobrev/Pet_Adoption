package com.example.service.impl;

import com.example.exceptions.UserNotFoundException;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.repositories.UserRepository;
import com.example.service.UserService;
import com.example.mapper.UserRegisterMapper;
import com.example.persistence.binding.UserRegisterBindingModel;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRegisterMapper userRegisterMapper;
    private final PasswordEncoder passwordEncoder;
    private JavaMailSender mailSender;

    @Override
    public UserEntity register(UserRegisterBindingModel userRegisterBindingModel) {
        String password = passwordEncoder.encode(userRegisterBindingModel.getUserPassword());
        userRegisterBindingModel.setUserPassword(password); // Set the encoded password back to the model
        UserEntity userEntity = userRegisterMapper.toUserEntity(userRegisterBindingModel, password);
        return userRepository.save(userEntity);
    }


    @Override
    public boolean emailExists(String email) {
        return userRepository.findByUserEmail(email) != null;
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByUserEmail(email);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(user.getUserEmail(), user.getUserPassword(), Collections.singleton(new SimpleGrantedAuthority(user.getRoles().toString())));
    }

    @Override
    public UserEntity loginUser(UserRegisterBindingModel userRegisterBindingModel) {
        UserDetails userDetails = this.loadUserByUsername(userRegisterBindingModel.getUserEmail());

        if (userDetails == null || !passwordEncoder.matches(userRegisterBindingModel.getUserPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid credentials!");
        }

        // If we've reached this point, the credentials are valid
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        // Get UserEntity
        UserEntity loginUser = this.findByEmail(userRegisterBindingModel.getUserEmail());

        return loginUser;
    }


    @Override
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        UserEntity user = userRepository.findByUserEmail(email);
        if (user != null) {
            user.setUserResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not find any user with the email " + email);
        }
    }

    @Override
    public UserEntity getByResetPasswordToken(String token) {
        return userRepository.findByUserResetPasswordToken(token);
    }

    @Override
    public void updatePassword(UserEntity user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setUserPassword(encodedPassword);

        user.setUserResetPasswordToken(null);
        userRepository.save(user);
    }

    @Override
    public void register(UserEntity user, String siteURL) throws UnsupportedEncodingException, MessagingException {
        String encodedPassword = passwordEncoder.encode(user.getUserPassword());
        user.setUserPassword(encodedPassword);

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        userRepository.save(user);

        sendVerificationEmail(user, siteURL);
    }

    @Override
    public void sendVerificationEmail(UserEntity user, String siteURL) throws MessagingException, UnsupportedEncodingException {
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
    public boolean verify(String verificationCode) {
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
