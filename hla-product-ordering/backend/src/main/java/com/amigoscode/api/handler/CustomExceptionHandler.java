package com.amigoscode.api.handler;


import com.amigoscode.api.response.ErrorResponse;
import com.amigoscode.domain.order.OrderAlreadyExistsException;
import com.amigoscode.domain.order.OrderNotFoundException;
import com.amigoscode.domain.user.UserAlreadyExistsException;
import com.amigoscode.domain.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return buildResponse(ex,  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException ex) {
        return buildResponse(ex,  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleOrderAlreadyExistsException(OrderAlreadyExistsException ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IOException.class)
    public final ResponseEntity<ErrorResponse> handleCommandNotSupportedException(IOException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ex.getMessage()));
    }

    private <E extends RuntimeException> ResponseEntity<ErrorResponse> buildResponse(E exception, HttpStatus status) {
        return ResponseEntity
                .status(status)
                .body(new ErrorResponse(exception.getMessage()));
    }

}