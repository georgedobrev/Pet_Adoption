    package com.example.persistence.entities;

    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.userdetails.UserDetails;

    import java.util.Arrays;
    import java.util.Collection;
    import java.util.List;
    import java.util.stream.Collectors;

    public class UserSecurityEntity implements UserDetails {

        private final UserEntity userEntity;

        public UserSecurityEntity(UserEntity userEntity){
            this.userEntity = userEntity;
        }


        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
        return userEntity.getAuthorities().stream().map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                    .collect(Collectors.toList());
        }

            //Modify the getAuthorities method in UserSecurityEntity to extract the authorities from the UserEntity object correctly.
        //Since your AuthorityEntity implements GrantedAuthority, you can directly access the authorities from UserEntity.
    //    @Override
    //    public Collection<? extends GrantedAuthority> getAuthorities() {
    //        return userEntity.getAuthorities();
    //    }



        @Override
        public String getPassword() {
            return userEntity.getUserPassword();
        }

        @Override
        public String getUsername() {
            return userEntity.getUserEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
