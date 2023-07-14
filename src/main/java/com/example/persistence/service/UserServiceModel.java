package com.example.persistence.service;

import com.example.persistence.view.AddressViewModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserServiceModel {
    private Long id;

    private String email;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String phone;
    private MultipartFile picture;
    private String profilePicture;

    private Set<AuthorityServiceModel> authorities;
}
