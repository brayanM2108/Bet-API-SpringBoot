package com.melo.bets.infrastructure.persistence;

import com.melo.bets.domain.dto.bet.BetCreateDto;
import com.melo.bets.domain.dto.bet.BetDto;
import com.melo.bets.domain.dto.bet.BetPriceDto;
import com.melo.bets.domain.dto.bet.BetUpdateDto;
import com.melo.bets.domain.exception.custom.BetNotFoundException;
import com.melo.bets.domain.exception.custom.CategoryNotExistException;
import com.melo.bets.domain.exception.custom.CompetitionNotExistException;
import com.melo.bets.domain.exception.custom.UserNotFoundException;
import com.melo.bets.domain.repository.IBetRepository;
import com.melo.bets.infrastructure.persistence.crud.BetCrudRepository;
import com.melo.bets.infrastructure.persistence.crud.CategoryCrudRepository;
import com.melo.bets.infrastructure.persistence.crud.CompetitionCrudRepository;
import com.melo.bets.infrastructure.persistence.crud.UserCrudRepository;
import com.melo.bets.infrastructure.persistence.entity.BetEntity;
import com.melo.bets.infrastructure.persistence.mapper.BetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Repository
public class BetRepositoryImpl implements IBetRepository {
    private final BetCrudRepository betCrudRepository;
    private final CompetitionCrudRepository competitionRepository;
    private final CategoryCrudRepository categoryRepository;
    private final UserCrudRepository userRepository;
    private final BetMapper betMapper;

    @Autowired
    public BetRepositoryImpl(BetCrudRepository betCrudRepository,
                             CompetitionCrudRepository competitionRepository,
                             CategoryCrudRepository categoryRepository,
                             UserCrudRepository userRepository,
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
        Optional<BetEntity> entity = betCrudRepository.findById(id);
        if (entity.isEmpty()) {
            throw new BetNotFoundException(id);
        }
        return entity.map(betMapper::toBetDto);
    }

    @Override
    public Optional<BetPriceDto> findPrice(UUID id) {
        if (!betCrudRepository.existsById(id)) throw new BetNotFoundException(id);
        return betCrudRepository.findBetPrice(id);
    }

    @Override
    public BetCreateDto save(BetCreateDto bet, MultipartFile file) {

        validateRelatedEntitiesExist(bet);

        BetEntity betEntity = betMapper.toBetCreateEntity(bet);

        return betMapper.toBetCreateDto(betCrudRepository.save(betEntity));
    }

   @Override
   public Optional<BetDto> update(UUID id, BetUpdateDto bet) {
       BetEntity betEntity = betCrudRepository.findById(id)
               .orElseThrow(() -> new BetNotFoundException(id));

       updateEntityFields(betEntity, bet);

       BetEntity updatedEntity = betCrudRepository.save(betEntity);
       return Optional.of(betMapper.toBetDto(updatedEntity));
   }

    @Override
    public void delete(UUID id) {
        if (betCrudRepository.findById(id).isEmpty()) throw new BetNotFoundException(id);
        betCrudRepository.deleteById(id);
    }

    @Override
    public Page<BetDto> findByCompetition(Pageable pageable, UUID competicionId) {
        validateCompetitionExists(competicionId);
        return betCrudRepository.findByCompetitionId(pageable, competicionId);
    }

    @Override
    public Page<BetDto> findByCategory(Pageable pageable, UUID categoryId) {
        validateCategoryExists(categoryId);
        return betCrudRepository.findByCategoryId(categoryId, pageable);
    }

    @Override
    public Page<BetDto> findAllAvailable(Pageable pageable) {
        return betCrudRepository.findByStatusTrue(pageable);
    }

    private void updateEntityFields(BetEntity entity, BetUpdateDto updateDto) {
        updateFieldIfNotNull(updateDto.title(), entity::setTitle);
        updateFieldIfNotNull(updateDto.description(), entity::setDescription);
        updateFieldIfNotNull(updateDto.odds(), entity::setOdds);
        updateFieldIfNotNull(updateDto.status(), entity::setStatus);
        updateFieldIfNotNull(updateDto.price(), entity::setPrice);
        updateFieldIfNotNull(updateDto.result(), entity::setResult);
        updateFieldIfNotNull(updateDto.betType(), entity::setBetType);
    }

    private <T> void updateFieldIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

    private void validateRelatedEntitiesExist(BetCreateDto bet) {
        validateUserExists(bet.userId());
        validateCompetitionExists(bet.competitionId());
    }

    private void validateUserExists(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
    }

    private void validateCompetitionExists(UUID competitionId) {
        if (!competitionRepository.existsById(competitionId)) {
            throw new CompetitionNotExistException(competitionId);
        }
    }

    private void validateCategoryExists(UUID categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotExistException(categoryId);
        }
    }
}
