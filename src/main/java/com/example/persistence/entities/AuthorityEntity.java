package com.example.persistence.entities;

import com.example.persistence.enums.RoleEnum;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "user_role")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class AuthorityEntity implements GrantedAuthority {

    @Column(name = "role_id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleID;
    @Enumerated(EnumType.STRING)
    private RoleEnum authority;

    @Override
    @Column(name = "user_role", nullable = false, unique = true)
    public String getAuthority() {
        return authority.name();
    }

    public void setAuthority(RoleEnum authority) {
        this.authority = authority;
    }

    public AuthorityEntity() {
    }
}