package com.melo.bets.web.exception;

import com.melo.bets.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BetNotFoundException.class)
    public ResponseEntity<Error> handleException(BetNotFoundException ex) {
        Error error = new Error("BetNotExist", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CompetitionNotExistException.class)
    public ResponseEntity<Error> handleException (CompetitionNotExistException ex){
        Error error = new Error("CompetitionNotExist", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Error> handleException (CategoryNotFoundException ex){
        Error error = new Error("CategoryNotExist", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BetNotUpdateException.class)
    public ResponseEntity<Error> handleException (BetNotUpdateException ex) {
        Error error = new Error("BetUpdateError", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> handleException (UserNotFoundException ex) {
        Error error = new Error("UserNotExist", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Error> handleException (EmailAlreadyExistsException ex) {
        Error error = new Error("UserAlreadyExist", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Error> handleException (InvalidCredentialsException ex) {
        Error error = new Error("InvalidCredentials", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DocumentAlreadyExistsException.class)
    public ResponseEntity<Error> handleException (DocumentAlreadyExistsException ex) {
        Error error = new Error("DocumentAlreadyExist", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    public ResponseEntity<Error> handleException (PaymentNotFoundException ex) {
        Error error = new Error("PaymentNotExist", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(UserDoesNotEnoughFundsException.class)
    public ResponseEntity<Error> handleException (UserDoesNotEnoughFundsException ex) {
        Error error = new Error("UserEnoughFund", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<Error>> handleException (MethodArgumentNotValidException ex) {
        List<Error> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach
                (fieldError -> errors.add(new Error(fieldError.getField(), fieldError.getDefaultMessage())));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException (Exception ex) {
        Error error = new Error("UnknownError", ex.getMessage());
        return ResponseEntity.internalServerError().body(error);
    }

}
