package com.melo.bets.domain.dto.user;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String name;
    private String email;
    private String password;
    private String document;
}
