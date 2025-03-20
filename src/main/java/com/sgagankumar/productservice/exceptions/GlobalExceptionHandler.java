package com.sgagankumar.productservice.exceptions;

import com.sgagankumar.productservice.dtos.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Objects;

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

    @ExceptionHandler(ProductDeletedException.class)
    public ResponseEntity<ErrorResponseDto> handleProductDeletedException(ProductDeletedException productDeletedException)
    {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatus("Error");
        errorResponseDto.setMessage(productDeletedException.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValid(MethodArgumentNotValidException methodArgumentNotValidException)
    {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatus("Invalid Argument");
        errorResponseDto.setMessage(methodArgumentNotValidException.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        logger.warn("Invalid argument type: {}. Expected type: {}", ex.getName(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatus("Error");
        errorResponseDto.setMessage("Invalid value for parameter: " + ex.getName());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        logger.warn("Unsupported request method: {}", ex.getMethod());

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatus("Error");
        errorResponseDto.setMessage("Request method " + ex.getMethod() + " is not supported");

        return new ResponseEntity<>(errorResponseDto, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingServletRequestParameter(MissingServletRequestParameterException ex) {
        logger.warn("Missing request parameter: {}", ex.getParameterName());

        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setStatus("Error");
        errorResponseDto.setMessage("Missing required parameter: " + ex.getParameterName());

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
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
