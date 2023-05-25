package controller;

import com.example.demo.persistance.entity.Animals;
import com.example.demo.persistance.entity.enums.AnimalSexEnum;
import com.example.demo.persistance.entity.enums.SizeCategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.DogService;

@RestController
@RequestMapping("/dogs")
public class DogController {
    DogService dog;
    private final DogService dogService;

    @Autowired
    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @PostMapping
    public ResponseEntity<Animals> addDog(@RequestBody Animals dog) {
        Animals addedDog = dogService.addDog(dog);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedDog);
    }
}
