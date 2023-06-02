package com.example.persistence.binding;

import com.example.persistence.enums.AnimalSexEnum;
import com.example.persistence.enums.AnimalSpeciesEnum;
import com.example.persistence.enums.SizeCategoryEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.bridge.Message;
import org.hibernate.validator.constraints.CodePointLength;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AnimalAddBindingModel {
    @NotBlank(message = "Please atleast one photo")
    private List<String> animalPhoto;
    @NotBlank(message = "Cannot be empty")
    @Length(min = 3, max = 20)
    private String animalName;
   private AnimalSpeciesEnum animalSpecies;
   private AnimalSexEnum animalGender;
    @NotBlank(message = "Cannot be empty")
    private int animalAge;
    private SizeCategoryEnum animalSize;
    @NotBlank(message = "Cannot be empty")
    @Length(min = 3, max = 55)
    private String animalCharacteristics;




}

