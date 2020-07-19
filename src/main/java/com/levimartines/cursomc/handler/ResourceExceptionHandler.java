package com.levimartines.cursomc.handler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.levimartines.cursomc.exceptions.AuthorizationException;
import com.levimartines.cursomc.exceptions.DataIntegrityException;
import com.levimartines.cursomc.exceptions.FileException;
import com.levimartines.cursomc.exceptions.ObjectNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e,
        HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(),
            HttpStatus.NOT_FOUND.value(), "Not found",
            e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e,
        HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(),
            HttpStatus.BAD_REQUEST.value(), "Data integrity",
            e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e,
        HttpServletRequest request) {
        ValidationError err = new ValidationError(System.currentTimeMillis(),
            HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error",
            e.getMessage(), request.getRequestURI());
        for (FieldError fieldErr : e.getBindingResult().getFieldErrors()) {
            err.addError(fieldErr.getField(), fieldErr.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandardError> authorization(AuthorizationException e,
        HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(),
            HttpStatus.FORBIDDEN.value(), "Access denied",
            e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<StandardError> file(FileException e,
        HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(),
            HttpStatus.BAD_REQUEST.value(), "File error",
            e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<StandardError> amazonService(AmazonServiceException e,
        HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.valueOf(e.getErrorCode());
        StandardError err = new StandardError(System.currentTimeMillis(),
            httpStatus.value(), "Amazon Service error",
            e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(httpStatus).body(err);
    }

    @ExceptionHandler(AmazonClientException.class)
    public ResponseEntity<StandardError> amazonClient(AmazonClientException e,
        HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(),
            HttpStatus.BAD_REQUEST.value(), "Amazon Client error",
            e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(AmazonS3Exception.class)
    public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e,
        HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(),
            HttpStatus.BAD_REQUEST.value(), "Amazon S3 error",
            e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
