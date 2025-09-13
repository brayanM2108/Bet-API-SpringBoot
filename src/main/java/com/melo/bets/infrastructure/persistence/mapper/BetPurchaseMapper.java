package com.melo.bets.infrastructure.persistence.mapper;



import com.melo.bets.domain.dto.betPurchase.*;
import com.melo.bets.infrastructure.persistence.entity.BetPurchaseEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BetMapper.class, UserMapper.class})
public interface BetPurchaseMapper {

    // --- Mappers for view bets ---
    @Mapping(target = "price", source = "betEntity.price")
    @Mapping(target = "betTitle", source = "betEntity.title")
    @Mapping(target = "userName", source = "userEntity.name")
    @Mapping(target = "creatorName", source = "betEntity.creator.name")
    BetPurchaseDto toBetPurchaseDto(BetPurchaseEntity entity);

    List<BetPurchaseDto> toBetPurchaseListDto(List<BetPurchaseEntity> entities);

    // --- Mapper for show creator bets ---
    @Mapping(target = "betTitle", source = "betEntity.title")
    @Mapping(target = "userName", source = "userEntity.name")
    @Mapping(target = "price", source = "betEntity.price")
    BetPurchaseCreatorDetailsDto toBetPurchaseCreatorDetailsDto(BetPurchaseEntity entity);

    List<BetPurchaseCreatorDetailsDto> toBetPurchaseCreatorDetailsDtoList(List<BetPurchaseEntity> entities);

    // --- Mapper for show user bets ---
    @Mapping(target = "creatorId", source = "betEntity.creator.id")
    @Mapping(target = "betTitle", source = "betEntity.title")
    @Mapping(target = "creatorName", source = "betEntity.creator.name")
    @Mapping(target = "price", source = "betEntity.price")
    BetPurchaseUserDetailsDto toBetPurchaseUserDetailsDto(BetPurchaseEntity entity);

    List<BetPurchaseUserDetailsDto> toBetPurchaseUserDetailsDtoList(List<BetPurchaseEntity> entities);


    // --- Mapper for create bets ---

    @InheritInverseConfiguration
    BetPurchaseEntity toBetPurchaseCreateEntity(BetPurchaseCreateDto betPurchase);

    BetPurchaseCreateDto toBetPurchaseCreateDto(BetPurchaseEntity betPurchase);

}
