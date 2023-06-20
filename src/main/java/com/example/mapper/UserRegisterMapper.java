package com.example.mapper;

import com.example.persistence.binding.UserRegisterBindingModel;
import com.example.persistence.entities.UserEntity;
import com.example.persistence.service.UserServiceModel;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserRegisterMapper {

    @Mapping(source = "userEmail", target = "email")
    UserServiceModel mapToUserServiceModel(UserEntity userEntity);

    @Mapping(target = "userFirstName", source = "userRegisterBindingModel.userFirstName")
    @Mapping(target = "userLastName", source = "userRegisterBindingModel.userLastName")
    @Mapping(target = "userEmail", source = "userRegisterBindingModel.userEmail")
    @Mapping(target = "userPhone", source = "userRegisterBindingModel.userPhone")
    @Mapping(target = "userPassword", source = "userRegisterBindingModel.userPassword")
    UserEntity toUserEntity(UserRegisterBindingModel userRegisterBindingModel, String password);
}
