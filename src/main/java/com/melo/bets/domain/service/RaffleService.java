package com.melo.bets.domain.service;

import com.melo.bets.domain.Raffle;
import com.melo.bets.persistence.RaffleRepositoryImpl;
import com.melo.bets.persistence.crud.RaffleCrudRepository;
import com.melo.bets.persistence.crud.UserCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RaffleService {

    private final RaffleRepositoryImpl raffleRepositoryImpl;
    private final UserCrudRepository userCrudRepository;

    @Autowired
    public RaffleService(RaffleRepositoryImpl raffleRepositoryImpl, RaffleCrudRepository raffleCrudRepository, UserCrudRepository userCrudRepository) {
        this.raffleRepositoryImpl = raffleRepositoryImpl;
        this.userCrudRepository = userCrudRepository;
    }

    public List<Raffle> getAllRaffles() {
        return raffleRepositoryImpl.findAll();
    }

    public Optional<Raffle> getRaffle(UUID id) {
        return raffleRepositoryImpl.findById(id);
    }

    public Raffle saveRaffle(Raffle raffle) {

        if (raffle.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required.");
        }

        // 2. Buscar el usuario en la base de datos
        userCrudRepository.findById(raffle.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + raffle.getUserId()));


        return raffleRepositoryImpl.save(raffle);
    }

    public Optional<Raffle> updateRaffle(Raffle raffle) {
        if (raffleRepositoryImpl.findById(raffle.getId()).isEmpty()) {
            return Optional.empty();
        }

        return raffleRepositoryImpl.update(raffle);
    }

    public boolean deleteRaffle(UUID id) {
        if (getRaffle(id).isPresent()) {
            raffleRepositoryImpl.delete(id);
            return true;
        }else return false;
    }
}
