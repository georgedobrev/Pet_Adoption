package com.example.service.impl;

import com.example.persistence.entities.UserEntity;
import com.example.persistence.entities.UserSecurityEntity;
import com.example.persistence.repositories.UserRepository;
import com.example.service.UserService;
import com.example.mapper.UserRegisterMapper;
import com.example.persistence.binding.UserRegisterBindingModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRegisterMapper userRegisterMapper;
    private final PasswordEncoder passwordEncoder;

    //private final AuthorityService authorityService;
    //private final AuthorityDepository authorityDepository;

    @Override
    public UserEntity register(UserRegisterBindingModel userRegisterBindingModel) {
        String password = passwordEncoder.encode(userRegisterBindingModel.getUserPassword());
        userRegisterBindingModel.setUserPassword(password); // Set the encoded password back to the model
        UserEntity userEntity = userRegisterMapper.toUserEntity(userRegisterBindingModel, password);
        return userRepository.save(userEntity);
    }


    @Override
    public boolean emailExists(String userEmail) {
        return userRepository.findByUserEmail(userEmail) != null;
    }

    @Override
    public UserEntity findByEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public UserDetails loadUserByUsername(String userEmail) {
        UserEntity user = this.userRepository.findByUserEmail(userEmail);
        if (user == null) {
            throw new UsernameNotFoundException(userEmail);
        }
        return new UserSecurityEntity(user);
    }
//    @Override
//    public UserDetails loadUserByUsername(String userEmail) {
//        Optional<UserEntity> user = this.userRepository.findByEmail(userEmail);
//        user.orElseThrow(() -> new UsernameNotFoundException(userEmail));
//        return user.map(UserSecurityEntity::new).get();
//    }

    @Override
    public UserEntity loginUser(UserRegisterBindingModel userRegisterBindingModel) {
        UserEntity userDetails = this.userRepository.findByUserEmail(userRegisterBindingModel.getUserEmail());


        // watch watch-shop

        return null;
    }

//    public String loginUser(@ModelAttribute("user") UserRegisterBindingModel user) {
//        UserDetails userDetails = userService.loadUserByUsername(user.getUserEmail());
//        if (userDetails != null && passwordEncoder.matches(user.getUserPassword(), userDetails.getPassword())) {
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
//                    userDetails.getPassword(),
//                    userDetails.getAuthorities());
//            SecurityContextHolder.getContext().setAuthentication(token);
//            return "redirect:/"; //userService
//        }
//        return "redirect:/users/login?error=true"; //add error
//    }
}
