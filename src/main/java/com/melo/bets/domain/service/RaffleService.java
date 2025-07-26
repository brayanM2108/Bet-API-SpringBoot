package com.melo.bets.domain.service;

import com.melo.bets.domain.Raffle;
import com.melo.bets.persistence.RaffleRepository;
import com.melo.bets.persistence.crud.RaffleCrudRepository;
import com.melo.bets.persistence.crud.UserCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RaffleService {

    private final RaffleRepository raffleRepository;
    private final UserCrudRepository userCrudRepository;

    @Autowired
    public RaffleService(RaffleRepository raffleRepository, RaffleCrudRepository raffleCrudRepository, UserCrudRepository userCrudRepository) {
        this.raffleRepository = raffleRepository;
        this.userCrudRepository = userCrudRepository;
    }

    public List<Raffle> getAllRaffles() {
        return raffleRepository.findAll();
    }

    public Optional<Raffle> getRaffle(UUID id) {
        return raffleRepository.findById(id);
    }

    public Raffle saveRaffle(Raffle raffle) {

        if (raffle.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required.");
        }

        // 2. Buscar el usuario en la base de datos
        userCrudRepository.findById(raffle.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + raffle.getUserId()));


        return raffleRepository.save(raffle);
    }

    public Optional<Raffle> updateRaffle(Raffle raffle) {
        if (raffleRepository.findById(raffle.getId()).isEmpty()) {
            return Optional.empty();
        }

        return raffleRepository.update(raffle);
    }

    public boolean deleteRaffle(UUID id) {
        if (getRaffle(id).isPresent()) {
            raffleRepository.delete(id);
            return true;
        }else return false;
    }
}
