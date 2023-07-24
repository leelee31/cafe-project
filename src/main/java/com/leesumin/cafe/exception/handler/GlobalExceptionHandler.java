package com.leesumin.cafe.exception.handler;

import com.leesumin.cafe.exception.BusinessException;
import com.leesumin.cafe.exception.ErrorResponse;
import com.leesumin.cafe.exception.NotEnoughPointException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotEnoughPointException.class)
    public ResponseEntity<ErrorResponse> notEnoughPointException(BusinessException e) {
        logger.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of(e));
    }
}