package com.pokez.configuration;

import com.pokez.exceptions.AccAlreadyExistsException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResponseHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String,String> handleValidationException(MethodArgumentNotValidException e){
    Map<String, String> errorMap = new HashMap<>();
    e.getBindingResult().getFieldErrors().forEach((error) -> {
      if (Objects.equals(error.getRejectedValue(), "") ||
          Objects.equals(error.getRejectedValue(),null))
        errorMap.put(error.getField(), error.getDefaultMessage());
      else
        errorMap.put(error.getField(),
            error.getRejectedValue()+" is an invalid value. "+error.getDefaultMessage());
    });
    return errorMap;
  }

  public ResponseEntity<Object> basicObjectResponse(Object responseObj,HttpStatus status) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("status", status.value());
    map.put("data", responseObj);
    return new ResponseEntity<Object>(map,status);
  }

  public ResponseEntity<Object> basicMessageResponse(String message, HttpStatus status) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("message", message);
    map.put("status", status.value());
    return new ResponseEntity<Object>(map,status);
  }


}
