package com.example.service;

import com.example.persistence.binding.UserRegisterBindingModel;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    UserEntity register(UserRegisterBindingModel userRegisterBindingModel);

    boolean emailExists(String email);

    UserServiceModel findByEmail(String email);

    List<UserEntity> getAllUsers();

    UserDetails loadUserByUsername(String userEmail);

    UserEntity loginUser(UserRegisterBindingModel userRegisterBindingModel);
}
