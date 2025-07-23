package com.melo.bets.persistence.mapper;

import com.melo.bets.domain.BetPurchase;
import com.melo.bets.persistence.entity.BetPurchaseEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, BetMapper.class})
public interface BetPurchaseMapper {

    BetPurchase toBetPurchase(BetPurchaseEntity betPurchaseEntity);

    @InheritInverseConfiguration
    BetPurchaseEntity toBetPurchaseEntity(BetPurchase betPurchase);
}
