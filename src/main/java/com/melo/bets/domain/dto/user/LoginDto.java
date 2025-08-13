package com.melo.bets.domain.dto.user;

import com.melo.bets.domain.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class LoginDto {

    private String email;

    private String password;

    private List<UserRole> roles;

    private Boolean status;
}
