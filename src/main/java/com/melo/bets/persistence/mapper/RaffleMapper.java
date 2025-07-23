package com.melo.bets.persistence.mapper;

import com.melo.bets.domain.Raffle;
import com.melo.bets.persistence.entity.RaffleEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface RaffleMapper {


    Raffle toRaffle(RaffleEntity raffleEntity);

    @InheritInverseConfiguration
    RaffleEntity toRaffleEntity(Raffle raffle);
}
