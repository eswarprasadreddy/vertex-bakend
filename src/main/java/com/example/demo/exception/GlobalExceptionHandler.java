package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> validationException(MethodArgumentNotValidException ex){
        Map<String,String> errors=new HashMap<String,String>();
        ex.getBindingResult().getFieldErrors().forEach(error->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> genericException(Exception ex){
        ex.printStackTrace();
        return ResponseEntity.status(500).body(ex.getMessage());
    }
    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<String> HandleNotFound(QuestionNotFoundException q){
        return new ResponseEntity<>(q.getMessage(),HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> argument(IllegalArgumentException cc){
        return new ResponseEntity<>(cc.getMessage(),HttpStatus.NOT_FOUND);
    }
}
