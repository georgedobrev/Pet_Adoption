package com.example.persistence.entities;

import com.example.persistence.enums.AnimalSexEnum;
import com.example.persistence.enums.AnimalSpeciesEnum;
import com.example.persistence.enums.SizeCategoryEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "animal")
public class AnimalsEntity {

    @Column(name = "animal_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long animalID;

    @JoinColumn(name = "shelter_id")
    @ManyToOne
    private SheltersEntity shelter;

    @ManyToOne
    @JoinColumn(name = "animal_photo_id")
    private AnimalPhotoEntity animalPhoto;

    @Column(name = "animal_name", nullable = false)
    private String animalName;

    @Column(name = "animal_species", nullable = false)
    @Enumerated(EnumType.STRING)
    private AnimalSpeciesEnum animalSpecies;

    @Column(name = "animal_sex", nullable = false)
    @Enumerated(EnumType.STRING)
    private AnimalSexEnum gender;

    @Column(name = "animal_age")
    private int animalAge;

    @Column(name = "size_category_id", nullable = false)
    @Enumerated(EnumType.STRING)
    private SizeCategoryEnum sizeCategory;


    @Column(name = "animal_characteristics")
    private String animalCharacteristics;

    @Column(name = "is_adopted")
    private boolean is_adopted;

    public AnimalsEntity() {
    }

}
