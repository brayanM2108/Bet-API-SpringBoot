package com.melo.bets.infrastructure.persistence.mapper;

import com.melo.bets.domain.dto.bet.BetCreateDto;
import com.melo.bets.domain.dto.bet.BetDto;
import com.melo.bets.domain.dto.bet.BetUpdateDto;
import com.melo.bets.infrastructure.persistence.entity.BetEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BetMapper {
    // --- Mappers for view bets ---
    @Mapping(target = "nameCategory", source = "competition.category.name")
    @Mapping(target = "nameCompetition", source = "competition.name")
    @Mapping(target = "nameCreator", source = "creator.name")
    BetDto toBetDto(BetEntity betEntity);


    // --- Mappers for update bets ---
    BetUpdateDto toBetUpdateDto(BetEntity betEntity);


    // --- Mappers for create bets ---
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "result", expression = "java(\"pendiente\")")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "competition", ignore = true)
    @Mapping(target = "status", expression = "java(true)")
    BetEntity toBetCreateEntity(BetCreateDto betCreateDto);

    BetCreateDto toBetCreateDto(BetEntity betEntity);

    // --- Methods inverse ---
    @InheritInverseConfiguration
    BetEntity toBetDtoEntity(BetDto betDto);

}
