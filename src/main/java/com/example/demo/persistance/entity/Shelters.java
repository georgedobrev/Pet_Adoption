package com.example.demo.persistance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "shelters")
public class Shelters {

    @Column(name = "shelter_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shelterID;

    @Column(name = "shelter_name")
    @NotNull
    private String shelterName;

    @Column(name = "shelter_city")
    @NotNull
    private String shelterCity;

    @Column(name = "shelter_address")
    @NotNull
    private String shelterAddress;

    @Column(name = "shelter_phone")
    private String shelterPhone;

    @Column(name = "shelter_email")
    private String shelterEmail;

    public Shelters() {
    }

}
