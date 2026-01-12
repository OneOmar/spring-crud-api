package com.omar.demoapi.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {

    private final String email;

    public UserPrincipal(String email) {
        this.email = email;
    }

    // Identity (username is email)
    @Override
    public String getUsername() {
        return email;
    }

    // Not used (JWT already authenticated)
    @Override
    public String getPassword() {
        return null;
    }

    // Roles and authorities
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    // Account status flags (keep true)
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

    public String getEmail() {
        return email;
    }
}
