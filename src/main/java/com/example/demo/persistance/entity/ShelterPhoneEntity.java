package com.example.demo.persistance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "shelters_phones")
@Getter
@Setter
public class ShelterPhoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shelter_phone_id")
    private long shelterPhoneID;

    @Column(name = "shelter_phone")
    @NotNull
    private String shelterPhone;
}
