package com.userservice.userservice.dtos;

import com.userservice.userservice.models.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogInResponseDto {
    private Token token;
}
