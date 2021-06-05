package amogilevskiy.microservices.auth.controller;

import amogilevskiy.microservices.auth.dto.ErrorResponseDto;
import amogilevskiy.microservices.auth.service.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    private ResponseEntity<Object> handleResourceAlreadyExists(
            UserAlreadyExistsException e, Locale locale) {
        return new ResponseEntity<>(new ErrorResponseDto(e.getMessage()), HttpStatus.CONFLICT);
    }

}
