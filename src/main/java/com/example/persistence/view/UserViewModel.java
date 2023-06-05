package com.example.persistence.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserViewModel {

    private String firstName;
    private String lastName;
    private String email;
    private String profilePicture;
    private AddressViewModel address;
    private Integer phone;
    private String password;
    private String confirmPassword;


}
