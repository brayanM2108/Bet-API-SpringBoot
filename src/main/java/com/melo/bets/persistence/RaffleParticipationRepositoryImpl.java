package com.melo.bets.persistence;

import com.melo.bets.domain.RaffleParticipation;
import com.melo.bets.domain.repository.IRaffleParticipationRepository;
import com.melo.bets.persistence.crud.RaffleParticipationCrudRepository;
import com.melo.bets.persistence.entity.RaffleParticipationEntity;
import com.melo.bets.persistence.mapper.RaffleParticipationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RaffleParticipationRepositoryImpl implements IRaffleParticipationRepository {

    private final RaffleParticipationCrudRepository raffleParticipationCrudRepository;
    private final RaffleParticipationMapper raffleParticipationMapper;

    @Autowired
    public RaffleParticipationRepositoryImpl(RaffleParticipationCrudRepository raffleParticipationCrudRepository, RaffleParticipationMapper raffleParticipationMapper) {
        this.raffleParticipationCrudRepository = raffleParticipationCrudRepository;
        this.raffleParticipationMapper = raffleParticipationMapper;
    }

    @Override
    public List<RaffleParticipation> findAll() {
        List<RaffleParticipationEntity> raffleParticipation = raffleParticipationCrudRepository.findAll();
        return raffleParticipationMapper.toRaffleParticipationList(raffleParticipation);
    }

    @Override
    public Optional<RaffleParticipation> findById(UUID id) {
        return raffleParticipationCrudRepository.findById(id).map(raffleParticipationMapper::toRaffleParticipation);
    }

    @Override
    public List<RaffleParticipation> findByUserId(UUID userid) {
        List<RaffleParticipationEntity> raffleParticipation = raffleParticipationCrudRepository.findByUserId(userid);
        return raffleParticipationMapper.toRaffleParticipationList(raffleParticipation);
    }

    @Override
    public List<RaffleParticipation> findByRaffleId(UUID betId) {
        List<RaffleParticipationEntity > raffleParticipation = raffleParticipationCrudRepository.findByRaffleId(betId);
        return raffleParticipationMapper.toRaffleParticipationList(raffleParticipation);
    }

    @Override
    public Optional<RaffleParticipation> getByUserAndRaffle(UUID userId, UUID raffleId) {
        return raffleParticipationCrudRepository.findByUserIdAndRaffleId(userId, raffleId)
                .map(raffleParticipationMapper::toRaffleParticipation);
    }

    @Override
    public RaffleParticipation save(RaffleParticipation raffleParticipation) {

        RaffleParticipationEntity raffleParticipationEntity = raffleParticipationMapper.toRaffleParticipationEntity(raffleParticipation);
        return raffleParticipationMapper.toRaffleParticipation(raffleParticipationCrudRepository.save(raffleParticipationEntity));
    }

    @Override
    public void delete(UUID id) {
        raffleParticipationCrudRepository.deleteById(id);
    }
}
