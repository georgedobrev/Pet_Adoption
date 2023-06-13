package com.example.service.impl;

import com.example.persistence.entities.UserEntity;
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
