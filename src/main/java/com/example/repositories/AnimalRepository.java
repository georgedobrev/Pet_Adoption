package com.example.repositories;

import com.example.entities.AnimalsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalRepository  extends JpaRepository<AnimalsEntity , Long> {
}