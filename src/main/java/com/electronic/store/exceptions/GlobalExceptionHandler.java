package com.electronic.store.exceptions;

import com.electronic.store.dtos.ApiResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resorceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        log.info("Resource Not Found Exception...!!");
        String message = ex.getMessage();
        ApiResponseMessage apiResponse = new ApiResponseMessage(HttpStatus.NOT_FOUND, false, message);
        return new ResponseEntity<ApiResponseMessage>(apiResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> map = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            map.put(field, message);
        });
        return new ResponseEntity<Map<String, String>>(map, HttpStatus.BAD_REQUEST);

    }
    //Handle bad api exception
    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponseMessage>handleBadApiRequest(BadApiRequest ex) {
        log.info("Bad Api Request..!!");
        String message = ex.getMessage();
        ApiResponseMessage apiResponse = new ApiResponseMessage(HttpStatus.BAD_REQUEST, false, message);
        return new ResponseEntity<ApiResponseMessage>(apiResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ApiResponseMessage>handleFileNotFound(FileNotFoundException ex) {
        log.info("File Not Found..!!");
        String message = ex.getMessage();
        ApiResponseMessage apiResponse = new ApiResponseMessage(HttpStatus.NOT_FOUND, false, message);
        return new ResponseEntity<ApiResponseMessage>(apiResponse, HttpStatus.NOT_FOUND);

    }
}

