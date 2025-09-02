package com.melo.bets.domain.exception;

import java.util.UUID;

public class CompetitionNotExistException extends RuntimeException {
    public CompetitionNotExistException(UUID id) {
        super("the competition with id " + id + " does not exist");
    }
}
