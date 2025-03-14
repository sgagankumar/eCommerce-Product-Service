package com.sgagankumar.productservice.exceptions;

import com.sgagankumar.productservice.dtos.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleProductNotFound(ProductNotFoundException productNotFoundException)
    {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatus("Error");
        errorResponseDto.setMessage(productNotFoundException.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(Exception exception)
    {
        logger.error("Unhandled exception occurred: {}", exception.getMessage(), exception);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatus("Error");
        errorResponseDto.setMessage("Something went wrong");
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
