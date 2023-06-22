package com.example.persistence.repositories;

import com.example.persistence.entities.LoginProviderEntity;
import com.example.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginProviderRepository extends JpaRepository<LoginProviderEntity, Long> {
    LoginProviderEntity findByUserId(UserEntity userEntity);
}
