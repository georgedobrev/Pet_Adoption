package com.example.persistence.binding;

import com.example.persistence.enums.AnimalSexEnum;
import com.example.persistence.enums.AnimalSpeciesEnum;
import com.example.persistence.enums.SizeCategoryEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter@Setter@NoArgsConstructor
public class UpdateAnimalBindingModel {

    private String animalName;
    @Min(value = 1, message = "Age must be greater than 0")
    private int animalAge;
    private SizeCategoryEnum AnimalSize;
    @NotBlank(message = "Cannot be empty")
    @Length(min = 3, max = 55)
    private String animalCharacteristics;
    private boolean adopted;

}
