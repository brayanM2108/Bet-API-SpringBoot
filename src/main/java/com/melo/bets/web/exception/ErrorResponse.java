package com.melo.bets.web.exception;

import lombok.Data;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Data
public class ErrorResponse {
    private String exception;
    private int status;
    private Timestamp timestamp;
    private String path;
    private Map<String, String> errors;

    public ErrorResponse(String exception, int status,  Timestamp timestamp, String path) {
        this.exception = exception;
        this.status = status;
        this.timestamp = timestamp;
        this.path = path;
        this.errors = new HashMap<>();
    }

    public ErrorResponse(String exception, int status, Timestamp timestamp, String path, Map<String, String> errors) {
        this.exception = exception;
        this.status = status;
        this.timestamp = timestamp;
        this.path = path;
        this.errors = errors;
    }
}

