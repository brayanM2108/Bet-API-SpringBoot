package com.melo.bets.domain.service;

import com.melo.bets.domain.dto.user.UserBalanceDto;
import com.melo.bets.domain.exception.user.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class BalanceService {

    private final UserService userService;

    public BalanceService(UserService userService) {
        this.userService = userService;
    }

    public BigDecimal getUserBalance(UUID userId) {
        return userService.getBalance(userId)
                .orElseThrow(() -> new UserNotFoundException(userId))
                .balance();
    }

    public boolean hasSufficientFunds(UUID userId, BigDecimal amount) {
        BigDecimal balance = getUserBalance(userId);
        return balance.compareTo(amount) >= 0;
    }

    @Transactional
    public void deductBalance(UUID userId, BigDecimal amount) {
        BigDecimal currentBalance = getUserBalance(userId);

        if (currentBalance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("The user does not have enough funds to buy the bet.");
        }

        BigDecimal newBalance = currentBalance.subtract(amount);
        userService.updateBalance(new UserBalanceDto(userId, newBalance));
    }

    @Transactional
    public void addBalance(UUID userId, BigDecimal amount) {
        BigDecimal currentBalance = getUserBalance(userId);
        BigDecimal newBalance = currentBalance.add(amount);
        userService.updateBalance(new UserBalanceDto(userId, newBalance));
    }
}
