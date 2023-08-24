package com.service.osiconnect.advice;

import com.service.osiconnect.constants.ApplicationConstants;
import com.service.osiconnect.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.service.osiconnect.constants.ApplicationConstants.*;


@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request) {

        JSONObject errorMap = new JSONObject();
        ex.getBindingResult()
            .getAllErrors()
            .forEach(
                (error) -> {
                    FieldError fieldErr = (FieldError) error;
                    errorMap.put(fieldErr.getField(), fieldErr.getDefaultMessage());
                });
        errorMap.put(ApplicationConstants.SUCCESS, false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorMap.toString());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleRootException(Exception exception) {
        log.error(
            "global error message {} and cause {} ",
            exception.getMessage(),
            exception.getCause());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                new JSONObject()
                    .put(MSG, MSG_REQUEST_FAILED)
                    .put(CODE, ERR_REQUEST_FAILED)
                    .put(SUCCESS, false)
                    .toString());
    }


    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<String> handleMetaException(ApplicationException applicationException) {
        log.error(
            "Application error message {} and cause {} ",
            applicationException.getMessage(),
            applicationException.getCause());
        return ResponseEntity.status(applicationException.getHttpStatus())
            .contentType(MediaType.APPLICATION_JSON)
            .body(applicationException.getMessage());
    }


    @ExceptionHandler(value = MissingRequestHeaderException.class)
    protected ResponseEntity<Object> handleRequestHeaderException(Exception ex) {
        log.error(ex.getMessage());
        JSONObject errorMap = new JSONObject();
        errorMap.put(MSG, ex.getMessage());
        errorMap.put(SUCCESS, FALSE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(errorMap.toString());
    }

}



