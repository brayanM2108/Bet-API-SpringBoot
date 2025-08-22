package com.melo.bets.domain.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class LoginDto {

    private String email;

    private String password;

    private List<UserRole> roles;

    private Boolean status;
}
