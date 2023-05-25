package com.example.demo.persistance.entity.repository;

import com.example.demo.persistance.entity.Animals;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public class AnimalRepository implements IAnimalRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Animals addDog(Animals dog) {
        entityManager.persist(dog);
        return dog;
    }
}