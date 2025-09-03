package com.melo.bets.domain.service;

import com.melo.bets.domain.dto.bet.BetCreateDto;
import com.melo.bets.domain.dto.bet.BetDto;
import com.melo.bets.domain.dto.bet.BetPriceDto;
import com.melo.bets.domain.dto.bet.BetUpdateDto;
import com.melo.bets.domain.exception.BetNotFoundException;
import com.melo.bets.domain.exception.BetNotUpdateException;
import com.melo.bets.domain.repository.IBetPurchaseRepository;
import com.melo.bets.domain.repository.IBetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Service
public class BetService {
    private final IBetRepository betRepository;
    private final StorageImageService storageImageService;
    private final IBetPurchaseRepository betPurchaseRepository;

    @Autowired
    public BetService(IBetRepository betRepository, StorageImageService storageImageService, IBetPurchaseRepository betPurchaseRepository) {
        this.betRepository = betRepository;
        this.storageImageService = storageImageService;
        this.betPurchaseRepository = betPurchaseRepository;
    }

    public Page<BetDto> getAll(int page, int elements) {
        Pageable pageRequest = PageRequest.of(page, elements);
        return betRepository.findAll(pageRequest);
    }

    public Optional<BetDto> get(UUID id) {
        return betRepository.findById(id);
    }

    public Page<BetDto> getAllAvailable(int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return betRepository.findAllAvailable(pageRequest);
    }

    public BetCreateDto saveBet(BetCreateDto bet, MultipartFile imageFile) throws Exception {

        // 1. Upload image
        String imageUrl = storageImageService.processAndUploadImage(imageFile, "bets");


        // 2. Crate DTO with image
        BetCreateDto betWithImage = new BetCreateDto(
                bet.title(),
                bet.description(),
                bet.odds(),
                bet.price(),
                bet.date(),
                bet.betType(),
                bet.competitionId(),
                bet.userId(),
                imageUrl
        );

        // 3. Save DTO and Image
        return betRepository.save(betWithImage, imageFile);
    }

    public Optional<BetPriceDto> getPrice(UUID id) {
        return betRepository.findPrice(id);
    }

    @Transactional
    public Optional<BetDto> update(UUID id, BetUpdateDto bet) {

        Optional<BetDto> currentBetOpt = this.get(id);
        if (currentBetOpt.isEmpty()) {
            throw new BetNotFoundException(id);
        }

        BetDto currentBet = currentBetOpt.get();
        validateUpdatePermissions(currentBet, bet);

        return betRepository.update(id, bet);
    }

    private void validateUpdatePermissions(BetDto currentBet, BetUpdateDto updateDto) {
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
            throw new BetNotUpdateException("Cannot modify bet details after bet date");
        }
    }

    private void validatePreDateUpdate(BetDto currentBet, BetUpdateDto updateDto) {
        // Before the day, verify if there are existing purchases
        if (updateDto.odds() != null && hasExistingPurchases(currentBet.id())) {
            throw new BetNotUpdateException("Cannot change odds when bet has purchases");
        }

        // Before the day, verify if there are existing purchases
        if (updateDto.price() != null && hasExistingPurchases(currentBet.id())) {
            throw new BetNotUpdateException("Cannot change price when bet has purchases");
        }

        // Don't allow a changing result before bet date
        if (updateDto.result() != null && !"pendiente".equals(updateDto.result())) {
            throw new BetNotUpdateException("Cannot set final result before bet date");
        }
    }

    private void validateResultChange(BetDto currentBet, String newResult) {
        // Solo permitir si la apuesta está activa

       /* if (!currentBet.status()) {
            throw new IllegalStateException("Cannot update result of inactive bet");
        }
        */

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

    //Verify if there are existing purchases for the bet
    private boolean hasExistingPurchases(UUID betId) {
        return !betPurchaseRepository.findByBetId(betId).isEmpty();
    }

    public void delete(UUID id) {
        betRepository.delete(id);
    }

    public Page<BetDto> findByCompetition(int page, int elements, UUID competicionId) {
        Pageable pageRequest = PageRequest.of(page, elements);
        return betRepository.findByCompetition(pageRequest, competicionId);
    }

    public Page<BetDto> findByCategory(int page, int elements, UUID categoryId) {
        Pageable pageRequest = PageRequest.of(page, elements);
        return betRepository.findByCategory(pageRequest, categoryId);
    }

}
