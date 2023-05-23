package com.example.demo.Models;

public class Shelters {
    private int shelterID;
    private String shelterName;
    private String shelterCity;
    private String shelterAddress;
    private String shelterPhone;
    private String shelterEmail;

    public Shelters() {
    }

    public Shelters(int shelterID, String shelterName, String shelterCity,
                    String shelterAddress, String shelterPhone,
                    String shelterEmail) {
        this.shelterID = shelterID;
        this.shelterName = shelterName;
        this.shelterCity = shelterCity;
        this.shelterAddress = shelterAddress;
        this.shelterPhone = shelterPhone;
        this.shelterEmail = shelterEmail;
    }

    public int getShelterID() {
        return shelterID;
    }

    public void setShelterID(int shelterID) {
        this.shelterID = shelterID;
    }

    public String getShelterName() {
        return shelterName;
    }

    public void setShelterName(String shelterName) {
        this.shelterName = shelterName;
    }

    public String getShelterCity() {
        return shelterCity;
    }

    public void setShelterCity(String shelterCity) {
        this.shelterCity = shelterCity;
    }

    public String getShelterAddress() {
        return shelterAddress;
    }

    public void setShelterAddress(String shelterAddress) {
        this.shelterAddress = shelterAddress;
    }

    public String getShelterPhone() {
        return shelterPhone;
    }

    public void setShelterPhone(String shelterPhone) {
        this.shelterPhone = shelterPhone;
    }

    public String getShelterEmail() {
        return shelterEmail;
    }

    public void setShelterEmail(String shelterEmail) {
        this.shelterEmail = shelterEmail;
    }

}
