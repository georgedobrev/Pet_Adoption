package controller;

import com.example.demo.persistance.entity.AnimalsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.DogService;

@RestController
@RequestMapping("/api/dogs")
public class DogController {
    DogService dog;
    private final DogService dogService;

    @Autowired
    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @PostMapping
    public ResponseEntity<AnimalsEntity> addDog(@RequestBody AnimalsEntity dog) {
        AnimalsEntity addedDog = dogService.addDog(dog);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedDog);
    }
}
