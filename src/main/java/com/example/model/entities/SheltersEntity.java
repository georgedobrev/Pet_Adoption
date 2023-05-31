package com.example.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "shelters")
public class SheltersEntity {

    @Column(name = "shelter_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long shelterID;

    @Column(name = "shelter_name", nullable = false)
    private String shelterName;

    @Column(name = "shelter_city")
    private String shelterCity;

    @Column(name = "shelter_address")
    private String shelterAddress;

    @JoinColumn(name = "shelter_phone_id")
    @ManyToOne
    private ShelterPhoneEntity shelterPhone;

    @Column(name = "shelter_email", unique = true)
    private String shelterEmail;

    public SheltersEntity() {
    }

}
