package com.example.demo.Models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetShelterRepo extends JpaRepository<Animals, Integer> {
}

//Отговаря за комуникацията с базата и изпращането на заявките
