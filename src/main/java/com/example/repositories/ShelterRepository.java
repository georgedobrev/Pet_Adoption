package com.example.repositories;

import com.example.persistence.entities.SheltersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShelterRepository extends JpaRepository<SheltersEntity , Long> {
}
