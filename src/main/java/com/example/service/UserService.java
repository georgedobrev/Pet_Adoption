package com.example.service;

import com.example.persistence.binding.UserRegisterBindingModel;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.enums.RoleEnum;

import java.util.List;

public interface UserService {
    UserEntity register(UserRegisterBindingModel userRegisterBindingModel);

    boolean emailExists(String email);

    UserEntity findByEmail(String email);

    List<UserEntity> getAllUsers();
}
