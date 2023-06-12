package com.example.mapper;

import com.example.persistence.binding.ShelterAddBindingModel;
import com.example.persistence.entities.ShelterPhoneEntity;
import com.example.persistence.entities.SheltersEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShelterMapper {

    @Mapping(target = "shelterName", source = "model.shelterName")
    @Mapping(target = "shelterCity", source = "model.shelterCity")
    @Mapping(target = "shelterAddress", source = "model.shelterAddress")
    @Mapping(target = "shelterEmail", source = "model.shelterEmail")
    SheltersEntity toShelterEntity(ShelterAddBindingModel model);

    @Mapping(target = "shelterName", source = "entity.shelterName")
    @Mapping(target = "shelterCity", source = "entity.shelterCity")
    @Mapping(target = "shelterAddress", source = "entity.shelterAddress")
    @Mapping(target = "shelterEmail", source = "entity.shelterEmail")
    ShelterAddBindingModel toShelterAddBindingModel(SheltersEntity entity);

    @Mapping(target = "shelter", source = "shelter")
    @Mapping(target = "shelterPhone", source = "phone")
    ShelterPhoneEntity toShelterPhoneEntity(SheltersEntity shelter, String phone);

}
