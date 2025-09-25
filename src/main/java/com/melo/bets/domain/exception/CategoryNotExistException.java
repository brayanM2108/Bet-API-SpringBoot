package com.melo.bets.domain.exception;

import java.util.UUID;

public class CategoryNotExistException extends RuntimeException {

    public CategoryNotExistException(UUID id) {
        super("The category with id " + id + " was not found");
    }
}
