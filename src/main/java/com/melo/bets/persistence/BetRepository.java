package com.melo.bets.persistence;

import com.melo.bets.domain.Bet;
import com.melo.bets.domain.repository.IBetRepository;
import com.melo.bets.persistence.crud.BetCrudRepository;
import com.melo.bets.persistence.entity.BetEntity;
import com.melo.bets.persistence.mapper.BetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BetRepository implements IBetRepository {
    private final BetCrudRepository betCrudRepository;
    private final BetMapper betMapper;

    @Autowired
    public BetRepository(BetCrudRepository betCrudRepository, BetMapper betMapper) {
        this.betCrudRepository = betCrudRepository;
        this.betMapper = betMapper;
    }


    @Override
    public List<Bet> findAll() {
        List<BetEntity> bets = betCrudRepository.findAll();
        return betMapper.toBetList(bets);
    }

    @Override
    public Optional<Bet> findById(UUID id) {
        return betCrudRepository.findById(id).map(betMapper::toBet);
    }

    @Override
    public Bet save(Bet bet) {
        BetEntity betEntity = betMapper.toBetEntity(bet);

        return betMapper.toBet(betCrudRepository.save(betEntity));
    }

    @Override
    public Optional <Bet> update(Bet bet) {
        Optional<BetEntity> existing = betCrudRepository.findById(bet.getId());
        if (existing.isPresent()) {
            BetEntity betEntity = betMapper.toBetEntity(bet);
            betEntity.setCreator(existing.get().getCreator());
            return Optional.of(betMapper.toBet(betCrudRepository.save(betEntity)));
        }
        return Optional.empty();
    }


    @Override
    public void delete(UUID id) {
        betCrudRepository.deleteById(id);
    }
}
