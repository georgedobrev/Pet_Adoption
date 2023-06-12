package com.example.mapper;

import com.example.persistence.binding.AnimalAddBindingModel;
import com.example.persistence.binding.UpdateAnimalBindingModel;
import com.example.persistence.entities.AnimalsEntity;
import com.example.persistence.entities.SheltersEntity;
import com.example.persistence.entities.SizeCategoryEntity;
import com.example.persistence.enums.SizeCategoryEnum;
import com.example.persistence.view.AddAnimalViewModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = MapperUtil.class)
@Component
public interface AnimalMapper {
    @Mapping(target = "animalName", source = "model.animalName")
    @Mapping(target = "animalSpecies", source = "model.animalSpecies")
    @Mapping(target = "gender", source = "model.animalGender")
    @Mapping(target = "animalAge", source = "model.animalAge")
    @Mapping(target = "sizeCategory", source = "sizeCategory")
    @Mapping(target = "animalCharacteristics", source = "model.animalCharacteristics")
    @Mapping(target = "shelter", source = "shelters")
    AnimalsEntity toEntity(AnimalAddBindingModel model, SizeCategoryEntity sizeCategory, SheltersEntity shelters);

    @Mapping(target = "animalName", source = "updateModel.animalName")
    @Mapping(target = "animalAge", source = "updateModel.animalAge")
    @Mapping(target = "animalCharacteristics", source = "updateModel.animalCharacteristics")
    @Mapping(target = "adopted", source = "updateModel.adopted")
    @Mapping(target = "sizeCategory", source = "sizeCategoryEntity")
    AnimalsEntity updateEntity(
            UpdateAnimalBindingModel updateModel, AnimalsEntity existingAnimal, SizeCategoryEntity sizeCategoryEntity
    );



    List<AnimalsEntity> toEntityList(List<AnimalAddBindingModel> models);

    List<AnimalAddBindingModel> toModelList(List<AnimalsEntity> entities);

    @Mapping(target = "animalSize", source = "existingAnimal.sizeCategory.category")
    @Mapping(target = "animalId", source = "existingAnimal.animalID")
    AddAnimalViewModel toAnimalViewModel(AnimalsEntity existingAnimal);
}

//   @Mapping(target = "animalPhotos", ignore = true)
//   @Mapping(source = "animalSize", target = "sizeCategory.category")
//   void updateEntityFromModel(AnimalAddBindingModel model, @MappingTarget AnimalsEntity entity);
//
//   @Mapping(target = "animalPhoto", source = "entity.animalPhotos", qualifiedBy = MapperUtil.MapPhotoList.class)
//   @Mapping(target = "animalSize", source = "entity.sizeCategory.category")
//   AddAnimalViewModel toViewModel(AnimalsEntity entity);
//
//   default AnimalAddBindingModel toModel(AnimalsEntity entity) {
//       AnimalAddBindingModel model = new AnimalAddBindingModel();
//       model.setAnimalName(entity.getAnimalName());
//       model.setAnimalSpecies(entity.getAnimalSpecies());
//       model.setAnimalGender(entity.getGender());
//       model.setAnimalAge(entity.getAnimalAge());
//       model.setAnimalSize(entity.getSizeCategory().getCategory());
//       model.setAnimalCharacteristics(entity.getAnimalCharacteristics());
//       model.setShelterID(entity.getShelter().getShelterID());
//       return model;

