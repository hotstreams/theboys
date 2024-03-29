package com.theboys.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = IncorrectRequest.class)
    public ResponseEntity<Object> handleIncorrectRequestException(BasicHttpException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getResponse(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(BasicHttpException exception, WebRequest request) {
        return handleExceptionInternal(exception, exception.getResponse(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BasicHttpException basicHttpException = new BasicHttpException("One of the required fields are missing or incorrect");
        return handleExceptionInternal(basicHttpException, basicHttpException.getResponse(), headers, status, request);
    }
}
