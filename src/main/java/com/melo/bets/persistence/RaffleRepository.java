package com.melo.bets.persistence;

import com.melo.bets.domain.Raffle;
import com.melo.bets.domain.repository.IRaffleRepository;
import com.melo.bets.persistence.crud.RaffleCrudRepository;
import com.melo.bets.persistence.entity.RaffleEntity;
import com.melo.bets.persistence.mapper.RaffleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public class RaffleRepository implements IRaffleRepository {

    private final RaffleCrudRepository raffleCrudRepository;
    private final RaffleMapper raffleMapper;

    @Autowired
    public RaffleRepository(RaffleCrudRepository raffleCrudRepository, RaffleMapper raffleMapper) {
        this.raffleCrudRepository = raffleCrudRepository;
        this.raffleMapper = raffleMapper;
    }

    @Override
    public List<Raffle> findAll() {
        List<RaffleEntity> raffles = raffleCrudRepository.findAll();
        return raffleMapper.toRaffleList(raffles);
    }

    @Override
    public Optional<Raffle> findById(UUID id) {
        return raffleCrudRepository.findById(id).map(raffleMapper::toRaffle);

    }

    @Override
    public Raffle save(Raffle raffle) {
        RaffleEntity raffleEntity = raffleMapper.toRaffleEntity(raffle);
        return raffleMapper.toRaffle(raffleCrudRepository.save(raffleEntity));
    }

    @Override
    public Optional <Raffle> update(Raffle raffle) {
        Optional<RaffleEntity> existing = raffleCrudRepository.findById(raffle.getId());
        if (existing.isPresent()) {
            RaffleEntity raffleEntity = raffleMapper.toRaffleEntity(raffle);
            raffleEntity.setCreator(existing.get().getCreator());
            return Optional.of(raffleMapper.toRaffle(raffleCrudRepository.save(raffleEntity)));
        }
        return Optional.empty();
    }

    @Override
    public void delete(UUID id) {
        raffleCrudRepository.deleteById(id);
    }
}
