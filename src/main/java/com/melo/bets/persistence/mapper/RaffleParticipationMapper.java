package com.melo.bets.persistence.mapper;

import com.melo.bets.domain.BetPurchase;
import com.melo.bets.domain.RaffleParticipation;
import com.melo.bets.persistence.entity.BetPurchaseEntity;
import com.melo.bets.persistence.entity.RaffleParticipationEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface RaffleParticipationMapper {


    RaffleParticipation toRaffleParticipation(RaffleParticipationEntity raffleParticipationEntity);
    List<RaffleParticipation> toRaffleParticipationList(List<RaffleParticipationEntity> raffleParticipationEntities);

    @InheritInverseConfiguration
    @Mapping(target = "raffleEntity", ignore = true)
    @Mapping(target = "userEntity", ignore = true)
    RaffleParticipationEntity toRaffleParticipationEntity(RaffleParticipation raffleParticipation);

}
