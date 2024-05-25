package com.userservice.userservice.dtos;

import com.userservice.userservice.models.Role;
import com.userservice.userservice.models.User;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String email;
    private String name;
    @ManyToMany
    private List<Role> roles;
    private Boolean isEmailVerified;

    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setIsEmailVerified(user.getIsEmailVerified());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
