package com.melo.bets.persistence.mapper;


import com.melo.bets.domain.BetPurchase;

import com.melo.bets.persistence.entity.BetPurchaseEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BetPurchaseMapper {

    @Mapping(target = "betId", source = "betEntity.id")
    @Mapping(target = "userId", source = "userEntity.id")
    BetPurchase toBetPurchase(BetPurchaseEntity betPurchaseEntity);
    List<BetPurchase> toBetPurchaseList(List<BetPurchaseEntity> betPurchaseEntities);


    @InheritInverseConfiguration
    BetPurchaseEntity toBetPurchaseEntity(BetPurchase betPurchase);
}
