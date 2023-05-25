package com.example.demo.persistance.entity;

import com.example.demo.persistance.entity.enums.AnimalSexEnum;
import com.example.demo.persistance.entity.enums.SizeCategoryEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "animal")
public class Animals {
    @Column(name = "animal_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int animalID;
    @JoinColumn(name = "shelter_id")
    @ManyToOne
    private Shelters shelter;
    @Column(name = "animal_photo")
    private String animalPhoto;
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
    private SizeCategory sizeCategory;

    @Column(name = "animal_characteristics")
    private String animalCharacteristics;

    // @Enumerated(EnumType.STRING)
    // private SizeCategoryEnum size;
    public Animals() {
    }

}
