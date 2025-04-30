package net.alten.gestion.handlerException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<Object> handlerApiRequestException(ApiRequestException e){
        ApiException apiException = new ApiException(e.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);

    }
}
