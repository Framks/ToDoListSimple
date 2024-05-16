package com.gabriel.tudosimples.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name ="usuario")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean active;
    private boolean accountExpired;
    private boolean accountLocked;
    private boolean credentialsExpired;

    public enum Role {
        ADMIN, CLIENTE, CONVIDADO
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.role.name())));
    }

    @Override
    public boolean isAccountNonExpired() {
        return !this.accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
