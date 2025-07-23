package com.melo.bets.persistence.mapper;

import com.melo.bets.domain.Bet;
import com.melo.bets.domain.BetPurchase;
import com.melo.bets.persistence.entity.BetEntity;
import com.melo.bets.persistence.entity.BetPurchaseEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, BetPurchaseMapper.class})
public interface BetMapper {


    Bet toBet(BetEntity betEntity);
    List<BetPurchase> toBetList(List<BetPurchaseEntity> betPurchaseEntities);

    @InheritInverseConfiguration
    BetEntity toBetEntity(Bet bet);
}
