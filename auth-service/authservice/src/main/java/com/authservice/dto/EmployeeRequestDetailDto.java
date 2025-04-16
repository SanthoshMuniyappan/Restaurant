package com.authservice.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ors.common.model.Employee;

import java.util.Collection;
import java.util.List;

public class EmployeeRequestDetailDto implements UserDetails {

    private String name;
    private String password;
    private List<GrantedAuthority> authorities;

    public EmployeeRequestDetailDto(Employee employee) {
        this.name = employee.getName();
        this.password = employee.getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority(employee.getRole().toString()));
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
        return name;
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
