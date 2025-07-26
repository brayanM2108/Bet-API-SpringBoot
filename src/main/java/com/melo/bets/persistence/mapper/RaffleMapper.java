package com.melo.bets.persistence.mapper;


import com.melo.bets.domain.Raffle;

import com.melo.bets.persistence.entity.RaffleEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR // This will throw an error if there are unmapped fields
)
public interface RaffleMapper {

    @Mapping(target = "userId", source = "creator.id")
    @Mapping(target = "numberOfTickets", source = "numberOfTickets")
    Raffle toRaffle(RaffleEntity raffleEntity);

    List<Raffle> toRaffleList(List<RaffleEntity> raffleEntities);

    @InheritInverseConfiguration
    RaffleEntity toRaffleEntity(Raffle raffle);
}
