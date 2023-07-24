package com.leesumin.cafe.exception.handler;

import com.leesumin.cafe.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<ErrorResponse> customerException(BusinessException e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorResponse.of(e));
    }

    @ExceptionHandler(PointException.class)
    public ResponseEntity<ErrorResponse> pointException(BusinessException e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorResponse.of(e));
    }

    @ExceptionHandler(MenuException.class)
    public ResponseEntity<ErrorResponse> menuException(BusinessException e) {
        logger.error(e.getMessage());
        return ResponseEntity.badRequest().body(ErrorResponse.of(e));
    }
}