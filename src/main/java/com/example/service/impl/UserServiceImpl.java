package com.example.service.impl;

import com.example.persistence.binding.UserAddBindingModel;
import com.example.persistence.entities.AuthorityEntity;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.enums.RoleEnum;
import com.example.persistence.repositories.AuthorityRepository;
import com.example.persistence.repositories.UserRepository;
import com.example.persistence.view.UserViewModel;
import com.example.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
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
        userViewModel.setAuthorities(existingUser.getAuthorities());
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

    @Override
    public void updateUserRoles(long id, Set<AuthorityEntity> newAuthorities) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No user found with id " + id));
        if (newAuthorities != null) {
            existingUser.setAuthorities(newAuthorities);
        }
        userRepository.save(existingUser);
    }

}
