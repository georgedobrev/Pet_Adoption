package com.example.service;

import com.example.model.entities.SheltersEntity;
import java.util.List;

public interface ShelterService {
    void createShelter(SheltersEntity shelter);
    SheltersEntity getShelterById(long shelterId);
    List<SheltersEntity> getAllShelters();
    void updateShelter(SheltersEntity shelter);
    void deleteShelter(SheltersEntity shelter);
}