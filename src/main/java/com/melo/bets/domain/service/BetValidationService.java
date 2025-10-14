package com.melo.bets.domain.service;

import com.melo.bets.domain.dto.bet.BetDto;
import com.melo.bets.domain.dto.bet.BetUpdateDto;
import com.melo.bets.domain.exception.custom.BetNotUpdateException;
import com.melo.bets.domain.repository.IBetPurchaseRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Service
public class BetValidationService {

    private final IBetPurchaseRepository betPurchaseRepository;

    public BetValidationService(IBetPurchaseRepository betPurchaseRepository) {
        this.betPurchaseRepository = betPurchaseRepository;
    }

    public boolean hasExistingPurchases(UUID betId) {
        return !betPurchaseRepository.findByBetId(betId).isEmpty();
    }

    public void validateUpdatePermissions(BetDto currentBet, BetUpdateDto updateDto) {
        LocalDateTime now = LocalDateTime.now();
        boolean isAfterBetDate = now.isAfter(currentBet.date());
        boolean isBetFinished = isResultFinal(currentBet.result());

        // 1. Verify if the bet is finished
        if (isBetFinished && updateDto.result() != null) {
            throw new BetNotUpdateException("Cannot change result of finished bet");
        }

        // 2. Validations after the date of bet
        if (isAfterBetDate) {
            validatePostDateUpdate(updateDto);
        } else {
            validatePreDateUpdate(currentBet, updateDto);
        }

        // 3. Verify changes of the result
        if (updateDto.result() != null) {
            validateResultChange(currentBet, updateDto.result());
        }

        // 4. Verify status (must be false after status)
        if (isAfterBetDate && updateDto.status() != null && updateDto.status()) {
            throw new BetNotUpdateException("Cannot activate bet after bet date");
        }
    }

    private void validatePostDateUpdate(BetUpdateDto updateDto) {
        // After the bet date, only the result can be modified
        if (updateDto.title() != null || updateDto.description() != null ||
                updateDto.odds() != null || updateDto.price() != null ||
                updateDto.betType() != null) {
            throw new BetNotUpdateException(" Cannot modify bet details after bet date");
        }
    }

    private void validatePreDateUpdate(BetDto currentBet, BetUpdateDto updateDto) {
        // Before the day, verify if there are existing purchases
        if (updateDto.odds() != null && hasExistingPurchases(currentBet.id())) {
            throw new BetNotUpdateException(" Cannot change odds when bet has purchases");
        }

        // Before the day, verify if there are existing purchases
        if (updateDto.price() != null && hasExistingPurchases(currentBet.id())) {
            throw new BetNotUpdateException(" Cannot change price when bet has purchases");
        }

        // Don't allow a changing result before bet date
        if (updateDto.result() != null && !"pendiente".equals(updateDto.result())) {
            throw new BetNotUpdateException("Cannot set final result before bet date");
        }
    }

    private void validateResultChange(BetDto currentBet, String newResult) {

        // Verify if the bet is active
        if (!currentBet.status()) {
            throw new IllegalStateException("Cannot update result of inactive bet");
        }

        // Verify if the result is valid
        if (!Arrays.asList("pendiente", "ganada", "perdida", "anulada").contains(newResult)) {
            throw new BetNotUpdateException("Invalid result value: " + newResult);
        }

        // Verify transitions
        if (isResultFinal(currentBet.result())) {
            throw new BetNotUpdateException("Cannot change result from final state");
        }
    }

    private boolean isResultFinal(String result) {
        if (result == null) return false;
        return Arrays.asList("ganada", "perdida", "anulada").contains(result);
    }
}
