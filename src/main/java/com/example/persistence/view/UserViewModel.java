package com.example.persistence.view;

import com.example.persistence.service.AuthorityServiceModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

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
    private Set<AuthorityServiceModel> authorities;


}
