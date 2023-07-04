package com.example.persistence.entities;

import com.example.persistence.enums.RoleEnum;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class AuthorityEntity implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private long roleID;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false)
    private RoleEnum authority;

//    @ManyToMany(mappedBy = "authorities")
//    private Set<UserEntity> users = new HashSet<>();


    @Override
    public String getAuthority() {
        return authority.name();
    }

    public void setAuthority(RoleEnum authority) {
        this.authority = authority;
    }

    public AuthorityEntity() {
    }
}