package com.example.demo.persistance.entity;

import com.example.demo.persistance.entity.enums.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {
    @Column(name = "user_id")
    @NotNull
    @Id
    private Integer userID;
    @Column(name = "user_first_name")
    @NotNull
    private String userFirstName;
    @Column(name = "user_last_name")
    @NotNull
    private String userLastName;
    @Column(name = "user_email")
    @NotNull
    private String userEmail;
    @Column(name = "user_password")
    @NotNull
    private String userPassword;
    @Column(name = "user_access_token")
    @NotNull
    private String userAccessToken;
    @Column(name = "user_refresh_token")
    @NotNull
    private String userRefreshToken;
    @Column(name = "user_role")
    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleEnum roles;

    public User() {
    }



}

