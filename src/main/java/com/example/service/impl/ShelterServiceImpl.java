package com.example.service.impl;

import com.example.persistence.binding.ShelterAddBindingModel;
import com.example.persistence.entities.ShelterPhoneEntity;
import com.example.persistence.entities.SheltersEntity;
import com.example.persistence.view.AddShelterViewModel;
import com.example.repositories.ShelterPhonesRepository;
import com.example.repositories.ShelterRepository;
import com.example.service.ShelterService;
import com.example.mapper.ShelterMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShelterServiceImpl implements ShelterService {

    private final ShelterRepository shelterRepository;
    private final ShelterPhonesRepository shelterPhonesRepository;
    private final ShelterMapper shelterMapper;

    @Autowired
    public ShelterServiceImpl(ShelterRepository shelterRepository, ShelterPhonesRepository shelterPhonesRepository, ShelterMapper shelterMapper) {
        this.shelterRepository = shelterRepository;
        this.shelterPhonesRepository = shelterPhonesRepository;
        this.shelterMapper = shelterMapper;
    }

    @Override
    public AddShelterViewModel getAddShelterViewModel() {

        return new AddShelterViewModel();
    }

    @Override
    public String addShelter(ShelterAddBindingModel shelterAddBindingModel) {
        SheltersEntity shelter = shelterMapper.toShelterEntity(shelterAddBindingModel);
        SheltersEntity savedShelter = shelterRepository.save(shelter);

        List<ShelterPhoneEntity> shelterPhoneEntities = new ArrayList<>();
        for (String phone : shelterAddBindingModel.getShelterPhones()) {
            ShelterPhoneEntity shelterPhoneEntity = new ShelterPhoneEntity();
            shelterPhoneEntity.setShelterPhone(phone);
            shelterPhoneEntity.setShelter(savedShelter);
            shelterPhoneEntities.add(shelterPhoneEntity);
        }

        shelterPhonesRepository.saveAll(shelterPhoneEntities);
        return "redirect:/shelters";
    }


    @Override
    public List<SheltersEntity> getAllShelters() {

        return shelterRepository.findAll();
    }




    @Override
    public AddShelterViewModel getShelterForEditing(long id) {
        SheltersEntity sheltersEntity = shelterRepository.findById(id).orElseThrow();
        ShelterAddBindingModel model = shelterMapper.toShelterAddBindingModel(sheltersEntity);
        AddShelterViewModel shelter = new AddShelterViewModel();
        BeanUtils.copyProperties(model, shelter);
        List<String> shelterPhones = shelterPhonesRepository.findAllByShelter(sheltersEntity)
                .stream()
                .map(ShelterPhoneEntity::getShelterPhone)
                .collect(Collectors.toList());
        shelter.setShelterPhone(shelterPhones);
        return shelter;
    }

    @Override
    public String updateShelter(long id, ShelterAddBindingModel shelterViewModel) {
        SheltersEntity shelter = shelterRepository.findById(id).orElseThrow();
        BeanUtils.copyProperties(shelterViewModel, shelter);
        List<ShelterPhoneEntity> shelterPhoneEntities = shelterViewModel.getShelterPhones().stream()
                .map(phone -> shelterMapper.toShelterPhoneEntity(shelter, phone))
                .collect(Collectors.toList());
        shelterPhonesRepository.saveAll(shelterPhoneEntities);
        shelterRepository.save(shelter);
        return "redirect:/shelters";
    }
}
