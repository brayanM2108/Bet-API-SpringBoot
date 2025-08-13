package com.melo.bets.persistence.mapper;

import com.melo.bets.domain.dto.bet.BetCreateDto;
import com.melo.bets.domain.dto.bet.BetDto;
import com.melo.bets.domain.dto.bet.BetUpdateDto;
import com.melo.bets.persistence.entity.BetEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BetMapper {
        @Mapping(target = "nameCategory", source = "competition.category.name")
        @Mapping(target = "nameCompetition", source = "competition.name")
        @Mapping(target = "nameCreator", source = "creator.name")
        BetDto toBetDto(BetEntity betEntity);

        BetUpdateDto toBetUpdateDto(BetEntity betEntity);
        @Mapping(target = "competition", ignore = true)
        @Mapping(target = "userId", ignore = true)
        @Mapping(target = "id", ignore = true)
        @Mapping(target = "creator", ignore = true)
        @Mapping(target = "competitionId", ignore = true)
        BetEntity toBetUpdateEntity(BetUpdateDto betUpdateDto);

        @InheritInverseConfiguration
        BetEntity toBetDtoEntity(BetDto betDto);

        BetCreateDto toBetCreateDto(BetEntity betEntity);

        List<BetDto> toBetDtoList(List<BetEntity> betEntities);
        List<BetEntity> toBetEntityList(List<BetDto> betDtos);


        @Mapping(target = "id", ignore = true)
        @Mapping(target = "creator", ignore = true)
        @Mapping(target = "competition", ignore = true)
        @Mapping(target = "status", expression = "java(true)") // valor por defecto
        @Mapping(target = "result", expression = "java(\"pendiente\")") // valor por defecto
        BetEntity toBetCreateEntity(BetCreateDto betCreateDto);
}
