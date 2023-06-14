package com.example.persistence.view;

import com.example.persistence.enums.AnimalSexEnum;
import com.example.persistence.enums.AnimalSpeciesEnum;
import com.example.persistence.enums.SizeCategoryEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AddAnimalViewModel {
  //  private List<String> animalPhoto;
    private String animalName;
    private AnimalSpeciesEnum animalSpecies;
    private AnimalSexEnum animalGender;
    private int animalAge;
    private SizeCategoryEnum sizeCategory;
    private String animalCharacteristics;
    private String shelterName;
    private long animalId;
    private boolean adopted;


}
