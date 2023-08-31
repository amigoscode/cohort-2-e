package com.amigoscode.api.handler;


import com.amigoscode.api.response.ErrorResponse;
import com.amigoscode.domain.email.EmailAlreadyExistsException;
import com.amigoscode.domain.email.EmailNotFoundException;
import com.amigoscode.domain.patient.PatientNotFoundException;
import com.amigoscode.domain.provider.ProviderAlreadyExistsException;
import com.amigoscode.domain.provider.ProviderNotFoundException;
import com.amigoscode.domain.schedule.ScheduleAlreadyExistsException;
import com.amigoscode.domain.schedule.ScheduleNotFoundException;
import com.amigoscode.domain.user.UserAlreadyExistsException;
import com.amigoscode.domain.user.UserNotFoundException;
import com.amigoscode.domain.version.VersionAlreadyExistsException;
import com.amigoscode.domain.version.VersionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PatientNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handlePatientNotFoundException(PatientNotFoundException ex) {
        return buildResponse(ex,  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return buildResponse(ex,  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ProviderNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleProviderNotFoundException(ProviderNotFoundException ex) {
        return buildResponse(ex,  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProviderAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleProviderAlreadyExistsException(ProviderAlreadyExistsException ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleEmailNotFoundException(EmailNotFoundException ex) {
        return buildResponse(ex,  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(ScheduleNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleScheduleNotFoundException(ScheduleNotFoundException ex) {
        return buildResponse(ex,  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ScheduleAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleScheduleAlreadyExistsException(ScheduleAlreadyExistsException ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(VersionNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleVersionNotFoundException(VersionNotFoundException ex) {
        return buildResponse(ex,  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VersionAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleVersionAlreadyExistsException(VersionAlreadyExistsException ex) {
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