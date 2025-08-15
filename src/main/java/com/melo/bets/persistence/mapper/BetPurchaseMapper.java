package com.melo.bets.persistence.mapper;


import com.melo.bets.domain.BetPurchase;

import com.melo.bets.domain.dto.betPurchase.BetPurchaseDto;
import com.melo.bets.persistence.entity.BetPurchaseEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BetPurchaseMapper {

    @Mapping(target = "betTitle", source = "betEntity.title")
    @Mapping(target = "userName", source = "userEntity.name")
    @Mapping(target = "creatorName", source = "betEntity.creator.name")
    BetPurchaseDto toBetPurchaseDto(BetPurchaseEntity entity);

    List<BetPurchaseDto> toBetPurchaseListDto(List<BetPurchaseEntity> entities);









    BetPurchase toBetPurchase(BetPurchaseEntity betPurchaseEntity);
    List<BetPurchase> toBetPurchaseList(List<BetPurchaseEntity> betPurchaseEntities);


    @InheritInverseConfiguration
    BetPurchaseEntity toBetPurchaseEntity(BetPurchase betPurchase);
}
