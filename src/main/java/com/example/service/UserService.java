package com.example.service;

import com.example.persistence.entities.UserEntity;
import com.example.persistence.enums.RoleEnum;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserEntity register(UserEntity userEntity);

    boolean emailExists(String email);

    UserEntity findByEmail(String email);

    List<UserEntity> getAllUsers();

    UserEntity changeRole(String email, RoleEnum authority);
}
