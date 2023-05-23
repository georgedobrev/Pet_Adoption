package com.example.demo.Models;

import com.example.demo.Models.Enums.RoleEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
public class User {
    private Integer userID;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPassword;
    private String userAccessToken;
    private String userRefreshToken;
    @Enumerated(EnumType.STRING)
    private RoleEnum roles;

    public User() {
    }

    public User(int userID, String userFirstName, String userLastName, String userEmail,
                String userPassword, String userAccessToken, String userRefreshToken, RoleEnum roles) {
        this.userID = userID;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userAccessToken = userAccessToken;
        this.userRefreshToken = userRefreshToken;
        this.roles = roles;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public RoleEnum getRoles() {
        return roles;
    }

    public void setRoles(RoleEnum roles) {
        this.roles = roles;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserAccessToken() {
        return userAccessToken;
    }

    public void setUserAccessToken(String userAccessToken) {
        this.userAccessToken = userAccessToken;
    }

    public String getUserRefreshToken() {
        return userRefreshToken;
    }

    public void setUserRefreshToken(String userRefreshToken) {
        this.userRefreshToken = userRefreshToken;
    }

}

