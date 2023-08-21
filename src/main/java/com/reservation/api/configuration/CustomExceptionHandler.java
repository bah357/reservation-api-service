package com.reservation.api.configuration;

import com.reservation.api.exception.BusinessException;
import com.reservation.api.model.reponse.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations = {RestController.class})
public class CustomExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler({BusinessException.class})
  public ResponseDto handleForceEventException(BusinessException e) {
    return ResponseDto.builder().message(e.getMessage()).build();
  }
}
