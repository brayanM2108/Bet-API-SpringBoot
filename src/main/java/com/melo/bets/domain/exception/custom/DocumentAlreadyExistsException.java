package com.melo.bets.domain.exception.custom;

public class DocumentAlreadyExistsException extends RuntimeException {
    public DocumentAlreadyExistsException(String document) {
        super("The document " + document + " already exists");
    }
}
