package com.example.persistence.entities;

import com.example.persistence.entities.SheltersEntity;
import jakarta.persistence.*;
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
    private String shelterPhone;

    @JoinColumn(name = "shelter_id")
    @ManyToOne
    private SheltersEntity shelter;
}
