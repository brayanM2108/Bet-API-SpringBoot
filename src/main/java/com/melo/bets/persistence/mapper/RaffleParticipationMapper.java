package com.melo.bets.persistence.mapper;

import com.melo.bets.domain.RaffleParticipation;
import com.melo.bets.persistence.entity.RaffleParticipationEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface RaffleParticipationMapper {


    RaffleParticipation toRaffleParticipation(RaffleParticipationEntity raffleParticipationEntity);

    @InheritInverseConfiguration
    RaffleParticipationEntity toRaffleParticipationEntity(RaffleParticipation raffleParticipation);

}
