//package com.example.service.impl;
//
//import com.example.persistence.entities.AuthorityEntity;
//import com.example.persistence.entities.UserEntity;
//import com.example.persistence.entities.UserSecurityEntity;
//import com.example.persistence.repositories.AuthorityRepository;
//import com.example.persistence.repositories.UserRepository;
//import com.example.persistence.service.UserServiceModel;
//import com.example.persistence.view.UserViewModel;
//import com.example.service.UserService;
//import com.example.persistence.enums.RoleEnum;
//import com.example.mapper.UserRegisterMapper;
//import com.example.persistence.binding.UserRegisterBindingModel;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collections;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class UserServiceImpl implements UserService {
//    private final UserRepository userRepository;
//    private final UserRegisterMapper userRegisterMapper;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthorityRepository authorityRepository;
//
//    //private final AuthorityService authorityService;
//
//
//
////    @Override
////    public UserEntity register(UserRegisterBindingModel userRegisterBindingModel) {
////        String password = passwordEncoder.encode(userRegisterBindingModel.getUserPassword());
////        userRegisterBindingModel.setUserPassword(password); // Set the encoded password back to the model
////        UserEntity userEntity = userRegisterMapper.toUserEntity(userRegisterBindingModel, password);
////        return userRepository.save(userEntity);
////    }
//        @Override
//        public UserEntity register(UserRegisterBindingModel userRegisterBindingModel) {
//            String password = passwordEncoder.encode(userRegisterBindingModel.getUserPassword());
//            userRegisterBindingModel.setUserPassword(password); // Set the encoded password back to the model
//            UserEntity userEntity = userRegisterMapper.toUserEntity(userRegisterBindingModel, password);
//
//            // assign default role USER authority
//            AuthorityEntity defaultAuthority = authorityRepository.findByAuthority(RoleEnum.USER);
//            userEntity.setAuthorities(Collections.singleton(defaultAuthority));
//
//            //migration seedAuthority..
//            return userRepository.save(userEntity);
//        }
//
//
//
//
//    @Override
//    public boolean emailExists(String userEmail) {
//
//            return userRepository.findByUserEmail(userEmail) != null;
//    }
//
//
//    //Doubt it works!? reason look at mapToUserServiceModel
//    //Need following method for loggedUserInfo in userController
//    //No implementation for now
//    @Override
//    @Transactional
//    public UserServiceModel findByEmail(String loggedUser) {
////        UserEntity user = this.userRepository.findByUserEmail(loggedUser);
////        UserServiceModel userServiceModel = userRegisterMapper.mapToUserServiceModel(user);
////        return userServiceModel;
//        //return userRegisterMapper.mapToUserServiceModel(user);
//
//        return null;
//    }
//
//
//
//
//
//    //-------------------------
////    @Override
////    @Transactional
////    public UserServiceModel findByUsername(String loggedUser) {
////        User user = this.userRepository.findByUsername(loggedUser);
////        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
////        return userServiceModel;
////    }
//
//    @Override
//    public List<UserEntity> getAllUsers() {
//        return userRepository.findAll();
//    }
//
//
//    @Override
//    public UserDetails loadUserByUsername(String userEmail) {
//        UserEntity user = this.userRepository.findByUserEmail(userEmail);
//        if (user == null) {
//            throw new UsernameNotFoundException(userEmail);
//        }
//        return new UserSecurityEntity(user);
//    }
//
//
//    @Override
//    public UserEntity loginUser(UserRegisterBindingModel userRegisterBindingModel) {
//        UserEntity user = this.userRepository.findByUserEmail(userRegisterBindingModel.getUserEmail());
//        if (user == null || !passwordEncoder.matches(userRegisterBindingModel.getUserPassword(), user.getUserPassword())) {
//            throw new BadCredentialsException("Invalid email/password provided");
//        }
//        return user;
//    }
//
////    public String loginUser(@ModelAttribute("user") UserRegisterBindingModel user) {
////        UserDetails userDetails = userService.loadUserByUsername(user.getUserEmail());
////        if (userDetails != null && passwordEncoder.matches(user.getUserPassword(), userDetails.getPassword())) {
////            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
////                    userDetails.getPassword(),
////                    userDetails.getAuthorities());
////            SecurityContextHolder.getContext().setAuthentication(token);
////            return "redirect:/"; //userService
////        }
////        return "redirect:/users/login?error=true"; //add error
////    }
//}
