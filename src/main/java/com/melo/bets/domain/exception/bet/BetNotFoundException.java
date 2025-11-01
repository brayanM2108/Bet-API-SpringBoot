package com.melo.bets.domain.exception.bet;

import java.util.UUID;

public class BetNotFoundException extends RuntimeException{

    public BetNotFoundException(UUID id){
        super("The bet with id " + id + " does not exist");
    }


}
