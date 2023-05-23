package com.example.demo.Models;

import com.example.demo.Models.Enums.AnimalSexEnum;
import com.example.demo.Models.Enums.SizeCategoryEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetRequest {
    private int animalID;
    private int animalShelterID;
    private String animalPhoto;
    private String animalName;

    @NotBlank
    @NotNull
    @Enumerated(EnumType.STRING)
    private AnimalSexEnum gender;

    @NotBlank
    @NotNull
    private int animalAge;
    private int animalSizeCategoryID;

    private String animalCharacteristics;

    @NotBlank
    @NotNull
    @Enumerated(EnumType.STRING)
    private SizeCategoryEnum size;
}
