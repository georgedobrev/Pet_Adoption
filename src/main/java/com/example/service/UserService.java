package com.example.service;

import com.example.persistence.binding.UserAddBindingModel;
import com.example.persistence.entities.AuthorityEntity;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.view.UserViewModel;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<UserEntity> getAllUsers();
    UserViewModel getUserById(long userId);
    void updateUser(long id, UserAddBindingModel userAddBindingModel);
}
