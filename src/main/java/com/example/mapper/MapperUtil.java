package com.example.mapper;

import com.example.persistence.entities.AnimalPhotoEntity;
import com.example.persistence.entities.SizeCategoryEntity;
import com.example.persistence.enums.SizeCategoryEnum;
import org.mapstruct.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

@Component
public class MapperUtil {

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    public @interface MapPhotoList {
    }


    @MapPhotoList
    public List<String> mapPhotost(List<AnimalPhotoEntity> animalPhotoEntityList) {
        return animalPhotoEntityList.stream().map(AnimalPhotoEntity::getAnimalPhotoURL).toList();
    }
}