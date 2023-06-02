package com.example.service.impl;

import com.example.persistence.entities.UserEntity;
import com.example.persistence.enums.RoleEnum;
import com.example.repositories.UserRepository;
import com.example.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserEntity register(UserEntity userEntity) {
        userEntity.setUserPassword(passwordEncoder.encode(userEntity.getUserPassword()));
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
    public UserEntity changeRole(String email, RoleEnum authority) {
        UserEntity user = userRepository.findByUserEmail(email);
        user.setRoles(authority);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(user.getUserEmail(), user.getUserPassword(), Collections.singleton(new SimpleGrantedAuthority(user.getRoles().toString())));
    }
}
