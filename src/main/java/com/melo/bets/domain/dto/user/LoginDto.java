package com.melo.bets.domain.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotBlank(message = "email not be blank")
    private String email;

    @NotBlank(message = "password not be blank")
    private String password;

    private List<UserRole> roles;

    private Boolean status;


}
