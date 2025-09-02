package com.melo.bets.infrastructure.persistence;

import com.melo.bets.domain.dto.bet.BetCreateDto;
import com.melo.bets.domain.dto.bet.BetDto;
import com.melo.bets.domain.dto.bet.BetPriceDto;
import com.melo.bets.domain.dto.bet.BetUpdateDto;
import com.melo.bets.domain.exception.BetNotFoundException;
import com.melo.bets.domain.repository.IBetRepository;
import com.melo.bets.domain.repository.ICategoryRepository;
import com.melo.bets.domain.repository.ICompetitionRepository;
import com.melo.bets.domain.repository.IUserRepository;
import com.melo.bets.infrastructure.persistence.crud.BetCrudRepository;
import com.melo.bets.infrastructure.persistence.entity.BetEntity;
import com.melo.bets.infrastructure.persistence.mapper.BetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Repository
public class BetRepositoryImpl implements IBetRepository {
    private final BetCrudRepository betCrudRepository;
    private final ICompetitionRepository competitionRepository;
    private final ICategoryRepository categoryRepository;
    private final IUserRepository userRepository;
    private final BetMapper betMapper;

    @Autowired
    public BetRepositoryImpl(BetCrudRepository betCrudRepository, 
                           ICompetitionRepository competitionRepository, 
                           ICategoryRepository categoryRepository, 
                           IUserRepository userRepository, 
                           BetMapper betMapper) {
        this.betCrudRepository = betCrudRepository;
        this.competitionRepository = competitionRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.betMapper = betMapper;
    }

    @Override
    public Page<BetDto> findAll(Pageable pageable) {
        return betCrudRepository.findAllProjected(pageable);
    }

    @Override
    public Optional<BetDto> findById(UUID id) {
        Optional<BetEntity> betEntity = betCrudRepository.findById(id);
        if (betEntity.isEmpty()) throw new BetNotFoundException(id);

        return betEntity.map(betMapper::toBetDto);
    }

    @Override
    public Optional<BetPriceDto> findPrice(UUID id) {
        findById(id);
        return betCrudRepository.findBetPrice(id);
    }

    @Override
    public BetCreateDto save(BetCreateDto bet, MultipartFile file) {
        userRepository.findById(bet.userId());

        competitionRepository.findById(bet.competitionId());

        BetEntity betEntity = betMapper.toBetCreateEntity(bet);

        return betMapper.toBetCreateDto(betCrudRepository.save(betEntity));
    }

   @Override
   public Optional<BetDto> update(UUID id, BetUpdateDto bet) {
       findById(id); // Esto lanzará BetNotFoundException si no existe

       BetEntity betEntity = betCrudRepository.findById(id).get();


       // update only fields not null
       if (bet.title() != null) betEntity.setTitle(bet.title());
       if (bet.description() != null) betEntity.setDescription(bet.description());
       if (bet.odds() != null) betEntity.setOdds(bet.odds());
       if (bet.status() != null) betEntity.setStatus(bet.status());
       if (bet.price() != null) betEntity.setPrice(bet.price());
       if (bet.result() != null) betEntity.setResult(bet.result());
       if (bet.betType() != null) betEntity.setBetType(bet.betType());

       return Optional.of(betMapper.toBetDto(betCrudRepository.save(betEntity)));
   }

    @Override
    public void delete(UUID id) {
        findById(id);
        betCrudRepository.deleteById(id);
    }

    @Override
    public Page<BetDto> findByCompetition(Pageable pageable ,UUID competicionId) {
        competitionRepository.findById(competicionId);
        return betCrudRepository.findByCompetitionId(pageable, competicionId);
    }

    @Override
    public Page<BetDto> findByCategory(Pageable pageable, UUID categoryId) {
        categoryRepository.findById(categoryId);
        return betCrudRepository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public Page<BetDto> findAllAvailable(Pageable pageable) {
        return betCrudRepository.findByStatusTrue(pageable);
    }
}
