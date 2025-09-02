package com.melo.bets.domain.repository;

import com.melo.bets.domain.dto.user.LoginDto;
import com.melo.bets.domain.dto.user.UserBalanceDto;
import com.melo.bets.domain.dto.user.UserDto;
import com.melo.bets.domain.dto.user.UserRegisterDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository {

    List<UserDto> findAll();

    Optional<UserDto> findById(UUID id);

    Optional<LoginDto> findByEmail(String email);

    Optional<UserBalanceDto> findBalance(UUID id);

    void updateBalance(UUID id, BigDecimal balance);

    UserRegisterDto save(UserRegisterDto user);

    Optional <UserDto> update(UserDto user);

    void delete(UUID id);
}
