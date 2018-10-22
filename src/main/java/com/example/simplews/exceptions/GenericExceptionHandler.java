package com.example.simplews.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ModelNotFoundException.class)
    public final ResponseEntity<Object> handleAllExceptions(ModelNotFoundException exception, WebRequest request) {
        return new ResponseEntity(new ExceptionResponse("Better call Saul. " + exception.getMessage(), new Date()),
                HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity(new ExceptionResponse("Better call Saul. Validation failed", new Date()),
                HttpStatus.BAD_REQUEST);
    }
}
