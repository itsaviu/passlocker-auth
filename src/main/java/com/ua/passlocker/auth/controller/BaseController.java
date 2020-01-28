package com.ua.passlocker.auth.controller;

import com.ua.passlocker.auth.exceptions.UserAlreadyExistException;
import com.ua.passlocker.auth.models.dto.ErrorResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BaseController {

    @ExceptionHandler(value = {UserAlreadyExistException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResp notExistException(Exception ex) {
        log.error(ex.getMessage());
        return new ErrorResp(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResp generalException(Exception ex) {
        log.error("Error while processing", ex);
        return new ErrorResp(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong!");
    }
}
