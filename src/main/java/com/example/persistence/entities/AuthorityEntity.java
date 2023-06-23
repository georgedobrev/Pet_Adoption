package com.example.persistence.entities;
import com.example.persistence.enums.RoleEnum;
import jakarta.persistence.*;
@Entity
@Table(name = "roles")
public class AuthorityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private long roleID;
    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false)
    private RoleEnum authority;
}