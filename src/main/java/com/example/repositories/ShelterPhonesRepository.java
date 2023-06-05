package com.example.repositories;

import com.example.persistence.entities.ShelterPhoneEntity;
import com.example.persistence.entities.SheltersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShelterPhonesRepository extends JpaRepository<ShelterPhoneEntity, Long> {
}
