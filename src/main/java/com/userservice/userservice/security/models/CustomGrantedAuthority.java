package com.userservice.userservice.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.userservice.userservice.models.Role;
import org.springframework.security.core.GrantedAuthority;
@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {
    private String authority;
    public CustomGrantedAuthority(){}
    public CustomGrantedAuthority(Role role){
        authority = role.getName();
    }
    @Override
    public String getAuthority() {
        return authority;
    }
}
