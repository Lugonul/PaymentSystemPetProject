package com.meshakin.rest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserAccess implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_login", nullable = false, unique = true)
    private String userLogin;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "user_role", nullable = false)
    private String userRole;

    @Version
    private Long version;

    @Column(columnDefinition = "boolean default true")
    private boolean enabled = true;

    @Column(name = "account_non_expired", columnDefinition = "boolean default true")
    private boolean accountNonExpired = true;

    @Column(name = "account_non_locked", columnDefinition = "boolean default true")
    private boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired", columnDefinition = "boolean default true")
    private boolean credentialsNonExpired = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userRole));
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userLogin;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
