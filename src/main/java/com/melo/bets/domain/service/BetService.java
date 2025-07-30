package com.melo.bets.domain.service;

import com.melo.bets.domain.Bet;
import com.melo.bets.domain.repository.IBetRepository;
import com.melo.bets.persistence.crud.UserCrudRepository;
import com.melo.bets.persistence.entity.BetEntity;
import com.melo.bets.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BetService {
    private final IBetRepository betRepository;
    private final UserCrudRepository userCrudRepository;


    @Autowired
    public BetService(IBetRepository betRepository, UserCrudRepository userCrudRepository) {
        this.betRepository = betRepository;
        this.userCrudRepository = userCrudRepository;
    }

    public List<Bet> getAllBets() {
        return betRepository.findAll();
    }

    public Optional<Bet> getBet(UUID id) {

        return betRepository.findById(id);
    }

    public Bet saveBet(Bet bet) {

        // 1. Verificar que el userId no sea null
        if (bet.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required.");
        }

        // 2. Buscar el usuario en la base de datos
        userCrudRepository.findById(bet.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + bet.getUserId()));

        if (bet.getDate() == null) {
            bet.setDate(LocalDateTime.now());
        }

        return betRepository.save(bet);
    }

    public Optional<Bet> updateBet(Bet bet) {
        {
            if (betRepository.findById(bet.getId()).isEmpty()) {
                return Optional.empty();
            }

            return betRepository.update(bet);
        }

    }

    public boolean deleteBet(UUID id) {
        if (getBet(id).isPresent()) {
            betRepository.delete(id);
            return true;
        }else return false;
    }

    public List<Bet> findByCompetition(UUID competicionId) {
        return betRepository.findByCompetition(competicionId);
    }

    public List<Bet> findByCategory(UUID categoryId) {
        return betRepository.findByCategory(categoryId);
    }

    public List<Bet> findByCompetitionAndCategory(UUID competitionId, UUID categoryId) {
        return betRepository.findByCompetitionAndCategory(competitionId, categoryId);
    }
}
