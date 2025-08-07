package com.melo.bets.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class User {

    private UUID id;

    private String name;

    private String email;

    private String password;

    private BigDecimal balance = BigDecimal.ZERO;

    private LocalDateTime registrationDate = LocalDateTime.now();

    private Boolean status;

    private String document;

    private List<UserRole> roles;
}
