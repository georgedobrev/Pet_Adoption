package com.example.persistence.entities;

import com.example.persistence.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {

    @Column(name = "user_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userID;

    @Column(name = "user_first_name")
    private String userFirstName;

    @Column(name = "user_last_name")
    private String userLastName;

    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "user_photo_url")
    private String userPhotoURL;

    @Column(name = "user_access_token")
    private String userAccessToken;

    @Column(name = "user_refresh_token")
    private String userRefreshToken;

    @Column(name = "user_reset_password_token")
    private String userResetPasswordToken;

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleEnum roles;

    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @Column(name = "enabled")
    private boolean enabled;
    public UserEntity() {
    }


}

