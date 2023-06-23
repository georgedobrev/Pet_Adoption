package com.example.persistence.entities;
import com.example.persistence.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import java.util.HashSet;
import java.util.Set;
@Entity
@Getter
@Setter
@Table(name = "users")
public class UserEntity {
    @Column(name = "user_id", nullable = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userID;
    @Column(name = "user_first_name", nullable = false)
    private String userFirstName;
    @Column(name = "user_last_name", nullable = false)
    private String userLastName;
    @Column(name = "user_email", nullable = false)
    private String userEmail;
    @Column(name = "user_password", nullable = false)
    private String userPassword;
    @Column(name = "user_photo_url")
    private String userPhotoURL;
    @Column(name = "user_access_token")
    private String userAccessToken;
    @Column(name = "user_refresh_token")
    private String userRefreshToken;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Set<AuthorityEntity> authorities = new HashSet<>();
    public UserEntity() {
    }
}