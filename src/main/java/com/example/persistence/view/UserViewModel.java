package com.example.persistence.view;

import com.example.persistence.entities.AuthorityEntity;
import com.example.persistence.enums.RoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserViewModel {
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPhone;
}
