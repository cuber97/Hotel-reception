package pl.edu.wat.backend.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.edu.wat.backend.dtos.AccountDto;

import java.util.Collection;

public class JwtUser implements UserDetails {

    private final Integer id;
    private final String username;
    private final String password;
    private final AccountDto accountEntity;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;

    JwtUser(Integer id, String username, String password, AccountDto accountDto, Collection<?
            extends GrantedAuthority> authorities, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.accountEntity = accountDto;
        this.authorities = authorities;
        this.enabled = enabled;
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    public AccountDto getAccountEntity() {
        return accountEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
