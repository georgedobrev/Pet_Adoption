package com.example.demo.persistance.entity;

import com.example.demo.persistance.entity.enums.AnimalSexEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

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

    @Column(name = "animal_name")
    @NotNull
    private String animalName;

    @Column(name = "animal_sex")
    @NotNull
    @Enumerated(EnumType.STRING)
    private AnimalSexEnum gender;

    @Column(name = "animal_age")
    private int animalAge;

    @JoinColumn(name = "size_category_id")
    @NotNull
    @ManyToOne
    private SizeCategoryEntity sizeCategory;

    @Column(name = "animal_characteristics")
    private String animalCharacteristics;
    @Column(name = "animal_isAdopted", columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    private boolean animalIsAdopted;

    public AnimalsEntity() {
    }

}
