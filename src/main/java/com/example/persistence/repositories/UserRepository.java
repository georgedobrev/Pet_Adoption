package com.example.persistence.repositories;

import com.example.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUserEmail(String email);
    //Optional<UserEntity> findByEmail(String userEmail);

    @Query("SELECT u FROM UserEntity u WHERE u.userEmail = ?1")
    public UserEntity findByEmail(String email);

    public UserEntity findByUserResetPasswordToken(String reset_password_token);
}

