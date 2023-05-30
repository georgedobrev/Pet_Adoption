package com.example.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "animal_photos")
public class AnimalPhotoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_photo_it")
    private long animalPhotoId;

    @Column(name = "animal_photo_url", nullable = false)
    private String animalPhotoURL;

    public AnimalPhotoEntity() {
    }
}
