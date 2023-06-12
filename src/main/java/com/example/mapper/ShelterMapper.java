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


}
