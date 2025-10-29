package com.melo.bets.domain.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    private UUID id;

    @NotBlank(message = "email not be blank")
    private String email;

    @NotBlank(message = "password not be blank")
    private String password;

    private List<UserRole> roles;

    private Boolean status;


}
