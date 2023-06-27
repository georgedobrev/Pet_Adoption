package com.example.service;

import com.example.exceptions.UserNotFoundException;
import com.example.persistence.binding.UserRegisterBindingModel;
import com.example.persistence.entities.UserEntity;
import jakarta.mail.MessagingException;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {
    UserEntity register(UserRegisterBindingModel userRegisterBindingModel);

    boolean emailExists(String email);

    UserEntity findByEmail(String email);

    List<UserEntity> getAllUsers();

    UserDetails loadUserByUsername(String userEmail);

    UserEntity loginUser(UserRegisterBindingModel userRegisterBindingModel);

    void updateResetPasswordToken(String token, String email) throws UserNotFoundException;

    UserEntity getByResetPasswordToken(String token);

    void updatePassword(UserEntity user, String newPassword);

    void register (UserEntity user, String siteURL) throws UnsupportedEncodingException, MessagingException;

    void sendVerificationEmail(UserEntity user, String siteURL) throws MessagingException, UnsupportedEncodingException;

    boolean verify(String verificationCode);

    }
