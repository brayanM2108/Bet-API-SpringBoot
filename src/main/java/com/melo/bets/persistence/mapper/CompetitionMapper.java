package com.melo.bets.persistence.mapper;

import com.melo.bets.domain.Competition;
import com.melo.bets.persistence.entity.CompetitionEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompetitionMapper {

    @Mapping(target = "categoryId", source = "category.id")
    Competition toCompetition(CompetitionEntity entity);

    @InheritInverseConfiguration
    @Mapping(target = "category", ignore = true) // Se setea manualmente
    CompetitionEntity toCompetitionEntity(Competition competition);

    List<Competition> toCompetitionList(List<CompetitionEntity> entities);
}
