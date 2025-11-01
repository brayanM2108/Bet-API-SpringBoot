package com.melo.bets.domain.service;

import com.melo.bets.domain.dto.bet.BetCreateDto;
import com.melo.bets.domain.dto.bet.BetDto;
import com.melo.bets.domain.dto.bet.BetPriceDto;
import com.melo.bets.domain.dto.bet.BetUpdateDto;
import com.melo.bets.domain.exception.bet.BetNotFoundException;
import com.melo.bets.domain.repository.IBetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;
import java.util.UUID;

@Service
public class BetService {
    private final IBetRepository betRepository;
    private final StorageImageService storageImageService;
    private final BetValidationService betValidationService;

    @Autowired
    public BetService(IBetRepository betRepository,
                      StorageImageService storageImageService,
                      BetValidationService betValidationService) {
        this.betRepository = betRepository;
        this.storageImageService = storageImageService;
        this.betValidationService = betValidationService;

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

    public BetCreateDto saveBet(BetCreateDto bet, UUID userId, MultipartFile imageFile) throws Exception {

        // 1. Upload image
        String imageUrl = storageImageService.processAndUploadImage(imageFile, "bets");


                // 2. Create DTO with image
        BetCreateDto betWithImage = new BetCreateDto(
                bet.title(),
                bet.description(),
                bet.odds(),
                bet.price(),
                bet.date(),
                bet.betType(),
                bet.competitionId(),
                imageUrl
        );

        // 3. Save DTO and Image
        return betRepository.save(betWithImage, userId, imageFile);
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
        betValidationService.validateUpdatePermissions(currentBet, bet);

        return betRepository.update(id, bet);
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
