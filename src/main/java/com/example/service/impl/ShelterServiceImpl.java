package com.example.service.impl;

import com.example.persistence.binding.ShelterAddBindingModel;
import com.example.persistence.entities.ShelterPhoneEntity;
import com.example.persistence.entities.SheltersEntity;
import com.example.persistence.view.AddShelterViewModel;
import com.example.repositories.ShelterPhonesRepository;
import com.example.repositories.ShelterRepository;
import com.example.service.ShelterService;
import mapper.ShelterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

//    @Override
//    public AddShelterViewModel getAddShelterViewModel() {
//        return new AddShelterViewModel();
//    }

    @Override
    public String addShelter(ShelterAddBindingModel shelterAddBindingModel) {
        ShelterPhoneEntity shelterPhone = shelterPhonesRepository.findByShelterPhoneID(shelterAddBindingModel.getShelterPhoneId());
        SheltersEntity shelter = shelterMapper.toShelterEntity(shelterAddBindingModel, shelterPhone);
        shelterRepository.save(shelter);
        return "redirect:/shelters";
    }
}

//    @Override
//    public AddShelterViewModel getShelterForEditing(long id) {
//        SheltersEntity sheltersEntity = shelterRepository.findById(id).orElseThrow();
//        AddShelterViewModel shelter = new AddShelterViewModel();
//        shelter.setShelterName(sheltersEntity.getShelterName());
//        shelter.setShelterCity(sheltersEntity.getShelterCity());
//        shelter.setShelterAddress(sheltersEntity.getShelterAddress());
//        shelter.setShelterEmail(sheltersEntity.getShelterEmail());
////        List<String> shelterPhones = sheltersEntity.getShelterPhone().stream()
////                .map(ShelterPhoneEntity::getShelterPhone)
////                .collect(Collectors.toList());
////        shelter.setShelterPhone(shelterPhones);
//        return shelter;
//    }
//
//    @Override
//    public String updateShelter(long id, ShelterAddBindingModel shelterViewModel) {
//        SheltersEntity shelter = shelterRepository.findById(id).orElseThrow();
//        shelter.setShelterName(shelterViewModel.getShelterName());
//        shelter.setShelterCity(shelterViewModel.getShelterCity());
//        shelter.setShelterAddress(shelterViewModel.getShelterAddress());
//        shelter.setShelterEmail(shelterViewModel.getShelterEmail());
////        List<ShelterPhoneEntity> shelterPhones = shelterViewModel.getShelterPhones().stream()
////                .map(phoneNumber -> {
////                    ShelterPhoneEntity shelterPhone = new ShelterPhoneEntity();
////                    return shelterPhone;
////                })
////                .collect(Collectors.toList());
////        shelter.setShelterPhone(shelterPhones);
//        shelterRepository.save(shelter);
//        return "redirect:/shelters";
//    }
//
//    @Override
//    public List<SheltersEntity> getAllShelters() {
//
//        return shelterRepository.findAll();
//    }
//}