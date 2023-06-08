package com.example.mapper;

import com.example.persistence.binding.UserRegisterBindingModel;
import com.example.persistence.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRegisterMapper {
//    UserViewModel modelToView(UserEntity user);
//    UserEntity viewToModel(UserViewModel userView);



//    @Mapping(target = "userID", ignore = true)
    @Mapping(target = "userFirstName")
    UserEntity toUserEntity(UserRegisterBindingModel bindingModel);

//    void updateUserEntity(UserRegisterBindingModel bindingModel, @MappingTarget UserEntity userEntity);
}
