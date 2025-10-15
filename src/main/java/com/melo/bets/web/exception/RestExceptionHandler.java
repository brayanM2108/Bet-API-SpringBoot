package com.melo.bets.web.exception;

import com.melo.bets.domain.exception.custom.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({
            BetNotFoundException.class,
            CompetitionNotExistException.class,
            CategoryNotExistException.class,
            UserNotFoundException.class,
            PaymentNotFoundException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundException(HttpServletRequest request, BetNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getClass().getSimpleName(),
                HttpStatus.NOT_FOUND.value(),
                new Timestamp(System.currentTimeMillis()),
                request.getRequestURI()
        );
        error.getErrors().put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler({
            BetNotUpdateException.class,
            EmailAlreadyExistsException.class,
            DocumentAlreadyExistsException.class,
            UserDoesNotEnoughFundsException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequestException(HttpServletRequest request, Exception ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST.value(),
                new Timestamp(System.currentTimeMillis()),
                request.getRequestURI()
        );
        error.getErrors().put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    @ExceptionHandler({
            InvalidCredentialsException.class,
    })
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(HttpServletRequest request, Exception ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getClass().getSimpleName(),
                HttpStatus.UNAUTHORIZED.value(),
                new Timestamp(System.currentTimeMillis()),
                request.getRequestURI()
        );
        error.getErrors().put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST.value(),
                new Timestamp(System.currentTimeMillis()),
                request.getRequestURI(),
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleUnknownException(HttpServletRequest request, Exception ex) {
////        // Evitar capturar excepciones de autenticación
////        if (ex instanceof AuthenticationException) {
////            throw (AuthenticationException) ex;
////        }
//
//        ErrorResponse error = new ErrorResponse(
//                "UnknownError",
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                new Timestamp(System.currentTimeMillis()),
//                request.getRequestURI()
//        );
//        return ResponseEntity.internalServerError().body(error);
//    }





}
