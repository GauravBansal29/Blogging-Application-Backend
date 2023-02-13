package com.blogging.blogapplication.Exceptions;

import java.io.ObjectInputStream.GetField;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// exception handling of controller
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> resourceNotFoundException(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(message, null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> resourceNotFoundException(NotFoundException ex) {
        String message = ex.getMessage();
        return new ResponseEntity<>(message, null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validationfailedException(MethodArgumentNotValidException ex) {

        Map<String, String> mp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((ele) -> {
            String errfield = ((FieldError) ele).getField(); // typecasttofielderrtogetfieldname
            String message = ele.getDefaultMessage();
            mp.put(errfield, message);
        });
        return new ResponseEntity<>(mp, null, HttpStatus.BAD_REQUEST);
    }

}
