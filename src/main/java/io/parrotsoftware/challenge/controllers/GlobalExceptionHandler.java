package io.parrotsoftware.challenge.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import io.parrotsoftware.challenge.errors.MyError;
import io.parrotsoftware.challenge.errors.MyValidationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<MyError>>> handleValidationErrors(
        MethodArgumentNotValidException ex) {

      List<FieldError> errors = ex.getBindingResult().getFieldErrors();
      List<MyError> myErrors = new ArrayList<MyError>();
      for ( FieldError error : errors) {
	myErrors.add(new MyError(error.getField(), error.getDefaultMessage()));
      }

      return new ResponseEntity<>(getErrorsMap(myErrors),
          new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, List<MyError>>> handleJsonBodyErrors(
        HttpMessageNotReadableException ex) {

      List<MyError> myErrors = new ArrayList<MyError>();
      myErrors.add(new MyError("body", "Bad JSON format"));
      return new ResponseEntity<>(getErrorsMap(myErrors),
          new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, List<MyError>>> handleMissingQueryParamErrors(
        MissingServletRequestParameterException ex) {

      List<MyError> myErrors = new ArrayList<MyError>();
      myErrors.add(new MyError(
          ex.getParameterName(), "Parameter is required"));
      return new ResponseEntity<>(getErrorsMap(myErrors),
          new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, List<MyError>>> handleWrongQueryParamErrors(
        MethodArgumentTypeMismatchException ex) {

      List<MyError> myErrors = new ArrayList<MyError>();
      myErrors.add(new MyError(ex.getName(), "Parameter type mismatch"));
      return new ResponseEntity<>(getErrorsMap(myErrors),
          new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MyValidationException.class)
    public ResponseEntity<Map<String, List<MyError>>> handleValidationErrors(
        MyValidationException ex) {

      List<MyError> myErrors = new ArrayList<MyError>();
      myErrors.add(ex.getMyError());
      return new ResponseEntity<>(getErrorsMap(myErrors),
          new HttpHeaders(), HttpStatus.BAD_REQUEST);

    }

    private Map<String, List<MyError>> getErrorsMap(List<MyError> errors) {
        Map<String, List<MyError>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

}
