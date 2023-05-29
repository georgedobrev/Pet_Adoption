package com.example.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "adoptions")
@Getter
@Setter
public class AdoptionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adoption_id")
    private long adoptionId;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private AnimalsEntity animal;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "adopter_first_name")
    @NotNull
    private String adopterFirstName;

    @Column(name = "adopter_last_name")
    @NotNull
    private String adopterLastName;

    @Column(name = "adopter_phone")
    @NotNull
    private String adopterPhone;

    @Column(name = "adopter_email")
    @NotNull
    private String adopterEmail;

    @Column(name = "adopter_address")
    @NotNull
    private String adopterAddress;

    @Column(name = "adoption_date")
    @NotNull
    private LocalDate adoptionDate;

    public AdoptionsEntity() {
    }
}