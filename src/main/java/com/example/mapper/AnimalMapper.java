package com.example.mapper;

import com.example.persistence.binding.AnimalAddBindingModel;
import com.example.persistence.entities.AnimalsEntity;
import com.example.persistence.view.AddAnimalViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = MapperUtil.class)
public interface AnimalMapper {

    @Mapping(source = "animalGender", target = "gender")
    AnimalsEntity toEntity(AnimalAddBindingModel model);

    @Mapping(target = "animalPhotos", ignore = true)
    @Mapping(source = "animalSize", target = "sizeCategory.category")
    void updateEntityFromModel(AnimalAddBindingModel model, @MappingTarget AnimalsEntity entity);

    @Mapping(target = "animalPhoto", source = "entity.animalPhotos", qualifiedBy = MapperUtil.MapPhotoList.class)
    @Mapping(target = "animalSize", source = "entity.sizeCategory.category")
    AddAnimalViewModel toViewModel(AnimalsEntity entity);

    default AnimalAddBindingModel toModel(AnimalsEntity entity) {
        AnimalAddBindingModel model = new AnimalAddBindingModel();
        model.setAnimalName(entity.getAnimalName());
        model.setAnimalSpecies(entity.getAnimalSpecies());
        model.setAnimalGender(entity.getGender());
        model.setAnimalAge(entity.getAnimalAge());
        model.setAnimalSize(entity.getSizeCategory().getCategory());
        model.setAnimalCharacteristics(entity.getAnimalCharacteristics());
        model.setShelterID(entity.getShelter().getShelterID());
        return model;
    }

    List<AnimalsEntity> toEntityList(List<AnimalAddBindingModel> models);

    List<AnimalAddBindingModel> toModelList(List<AnimalsEntity> entities);
}

