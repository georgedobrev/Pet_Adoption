package com.example.service.impl;

import com.example.persistence.binding.UserAddBindingModel;
import com.example.persistence.entities.AuthorityEntity;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.repositories.UserRepository;
import com.example.persistence.view.UserViewModel;
import com.example.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserViewModel getUserById(long userId) {
        UserEntity existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No user found with id " + userId));
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setUserFirstName(existingUser.getUserFirstName());
        userViewModel.setUserLastName(existingUser.getUserLastName());
        userViewModel.setUserEmail(existingUser.getUserEmail());
        userViewModel.setUserPhone(existingUser.getUserPhone());
        return userViewModel;
    }

    @Override
    public void updateUser(long id, UserAddBindingModel userAddBindingModel) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No user found with id " + id));
        existingUser.setUserFirstName(userAddBindingModel.getUserFirstName());
        existingUser.setUserLastName(userAddBindingModel.getUserLastName());
        existingUser.setUserPhone(userAddBindingModel.getUserPhone());
        existingUser.setUserEmail(userAddBindingModel.getUserEmail());
        userRepository.save(existingUser);
    }
}
