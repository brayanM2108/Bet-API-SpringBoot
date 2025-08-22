package com.melo.bets.domain.service;

import com.melo.bets.domain.dto.bet.BetCreateDto;
import com.melo.bets.domain.dto.bet.BetDto;
import com.melo.bets.domain.dto.bet.BetPriceDto;
import com.melo.bets.domain.dto.bet.BetUpdateDto;
import com.melo.bets.domain.repository.IBetRepository;
import com.melo.bets.infrastructure.persistence.crud.UserCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class BetService {
    private final IBetRepository betRepository;
    private final UserCrudRepository userCrudRepository;
    private final StorageImageService storageImageService;

    @Autowired
    public BetService(IBetRepository betRepository, UserCrudRepository userCrudRepository, StorageImageService storageImageService) {
        this.betRepository = betRepository;
        this.userCrudRepository = userCrudRepository;
        this.storageImageService = storageImageService;
    }

    public Page<BetDto> getAllBets(int page, int elements) {
        Pageable pageRequest = PageRequest.of(page, elements);
        return betRepository.findAll(pageRequest);
    }

    public Optional<BetDto> getBet(UUID id) {

        return betRepository.findById(id);
    }

    public Page<BetDto> getAllAvailable(int page, int elements, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, elements, sort);
        return betRepository.findAllAvailable(pageRequest);
    }

    public BetCreateDto saveBet(BetCreateDto bet, MultipartFile imageFile) throws Exception {

        // 1. Verificar que el userId no sea null
        if (bet.userId() == null) {
            throw new IllegalArgumentException("User ID is required.");
        }

        // 2. Verificar que el usuario existe
        final BetCreateDto originalBet = bet;
        userCrudRepository.findById(originalBet.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + originalBet.userId()));


        // 3. Subir imagen
        String imageUrl = storageImageService.processAndUploadImage(imageFile, "bets");



        // 4. Crear DTO completo con URL de imagen
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

        // 5. Guardar usando el DTO con imagen
        return betRepository.save(betWithImage, imageFile);
    }

    public Optional<BetPriceDto> getPrice(UUID id) {
        return betRepository.findPrice(id);
    }

    public Optional<BetUpdateDto> updateBet(UUID id, BetUpdateDto bet) {
        // Validaciones de negocio
        if (bet.title() != null && bet.title().isBlank()) {
            throw new IllegalArgumentException("The tittle cannot be empty.");
        }
        if (bet.odds() != null && bet.odds().compareTo(BigDecimal.ONE) < 1) {
            throw new IllegalArgumentException("The odds must be greater than or equal to one.");
        }
        if (bet.price() != null && bet.price().compareTo(BigDecimal.ZERO) <0) {
            throw new IllegalArgumentException("The price must be greater than or equal to zero.");
        }
        if (bet.date() != null && bet.date().isBefore(java.time.LocalDateTime.now())) {
            throw new IllegalArgumentException("The date cannot be in the past.");
        }

        return betRepository.update(id, bet);
    }

    public boolean deleteBet(UUID id) {
        if (getBet(id).isPresent()) {
            betRepository.delete(id);
            return true;
        }
        return false;
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
