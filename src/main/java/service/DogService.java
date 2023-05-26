package service;

import com.example.demo.persistance.entity.AnimalsEntity;
import com.example.demo.persistance.entity.repository.IAnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DogService {
    private final IAnimalRepository animalRepository;

    @Autowired
    public DogService(IAnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public AnimalsEntity addDog(AnimalsEntity dog) {
        return animalRepository.addDog(dog);
    }
}