package com.example.persistence.repositories;

import com.example.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
    boolean existsByUserEmail(String userEmail);

    UserEntity findByUserEmail(String userEmail);
}
