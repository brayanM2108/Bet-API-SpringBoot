package com.melo.bets.domain.dto.user;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class UserDto {

    private UUID id;
    private String name;
    private String email;
    private BigDecimal balance;
    private LocalDateTime registrationDate = LocalDateTime.now();;
    private Boolean status;
    private List<UserRole> roles;
}
