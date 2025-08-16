package com.melo.bets.domain.service;


import com.melo.bets.domain.RaffleParticipation;
import com.melo.bets.persistence.RaffleParticipationRepositoryImpl;
import com.melo.bets.persistence.crud.RaffleCrudRepository;
import com.melo.bets.persistence.crud.UserCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RaffleParticipationService {

    private final RaffleParticipationRepositoryImpl raffleParticipationRepository;
    private final UserCrudRepository userCrudRepository;
    private final RaffleCrudRepository raffleCrudRepository;

    @Autowired
    public RaffleParticipationService(RaffleParticipationRepositoryImpl raffleParticipationRepository, UserCrudRepository userCrudRepository, RaffleCrudRepository raffleCrudRepository) {
        this.raffleParticipationRepository = raffleParticipationRepository;
        this.userCrudRepository = userCrudRepository;
        this.raffleCrudRepository = raffleCrudRepository;
    }

    public List<RaffleParticipation> getAll(){
        return raffleParticipationRepository.findAll();
    }

    public Optional<RaffleParticipation> getById(UUID id) {
        return raffleParticipationRepository.findById(id);
    }

    public List<RaffleParticipation> getByUser(UUID userId) {
        return raffleParticipationRepository.findByUserId(userId);
    }

    public List<RaffleParticipation> getByRaffle(UUID betId) {
        return raffleParticipationRepository.findByRaffleId(betId);
    }

    public Optional<RaffleParticipation> getByUserAndRaffle(UUID userId, UUID betId) {
        return raffleParticipationRepository.getByUserAndRaffle(userId, betId);
    }

    public RaffleParticipation save(RaffleParticipation raffleParticipation) {
        UUID userId = raffleParticipation.getUserId();
        UUID raffleId = raffleParticipation.getRaffleId();

        if (userId == null) {
            throw new IllegalArgumentException("User ID is required.");
        }

        if (raffleId == null) {
            throw new IllegalArgumentException("Raffle ID is required.");
        }

        // Verifica que el usuario exista
        boolean userExists = userCrudRepository.existsById(userId);
        if (!userExists) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        // Verifica que la rifa exista
        boolean raffleExists = raffleCrudRepository.existsById(raffleId);
        if (!raffleExists) {
            throw new IllegalArgumentException("Raffle not found with ID: " + raffleId);
        }

        return raffleParticipationRepository.save(raffleParticipation);
    }

    public boolean delete(UUID id) {
        if (getById(id).isPresent()) {
            raffleParticipationRepository.delete(id);
            return true;
        } return false;
    }
}
