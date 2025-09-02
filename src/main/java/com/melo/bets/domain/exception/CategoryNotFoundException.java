package com.melo.bets.domain.exception;

import java.util.UUID;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(UUID id) {
        super("The category with id " + id + " was not found");
    }
}
