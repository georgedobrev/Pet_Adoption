package com.example.entities;

import com.example.entities.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

    @Column(name = "user_id", nullable = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userID;

    @Column(name = "user_first_name", nullable = true)
    private String userFirstName;

    @Column(name = "user_last_name", nullable = true)
    private String userLastName;

    @Column(name = "user_email", nullable = true)
    private String userEmail;

    @Column(name = "user_password", nullable = true)
    private String userPassword;

    @Column(name = "user_photo_url")
    private String userPhotoURL;

    @Column(name = "user_access_token")
    private String userAccessToken;

    @Column(name = "user_refresh_token")
    private String userRefreshToken;

    @Column(name = "user_role", nullable = true)
    @Enumerated(EnumType.STRING)
    private RoleEnum roles;

    public UserEntity() {
    }


}

