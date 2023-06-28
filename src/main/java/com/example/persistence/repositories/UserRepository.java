package com.example.persistence.repositories;

import com.example.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findUserByUserEmail(String email);

    Optional<UserEntity> findByUserEmail(String userEmail);
}
