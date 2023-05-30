package com.example.service.impl;

import com.example.model.entities.SheltersEntity;
import com.example.repositories.ShelterRepository;
import com.example.service.ShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShelterServiceImpl implements ShelterService {

    private ShelterRepository shelterRepository;

    @Autowired
    public ShelterServiceImpl(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    @Override
    public void createShelter(SheltersEntity shelter) {
        shelterRepository.save(shelter);
    }

    @Override
    public SheltersEntity getShelterById(long shelterId) {
        return shelterRepository.findById(shelterId).orElse(null);
    }

    @Override
    public List<SheltersEntity> getAllShelters() {
        return shelterRepository.findAll();
    }

    @Override
    public void updateShelter(SheltersEntity shelter) {
        shelterRepository.save(shelter);
    }

    @Override
    public void deleteShelter(SheltersEntity shelter) {
        shelterRepository.delete(shelter);
    }
}