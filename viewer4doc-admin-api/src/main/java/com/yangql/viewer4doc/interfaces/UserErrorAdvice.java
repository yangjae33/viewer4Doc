package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.EmailExistedException;
import com.yangql.viewer4doc.application.MaxGroupCountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserErrorAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailExistedException.class)
    public String handleEmailNotExisted(){
        return "Existed Email";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MaxGroupCountException.class)
    public String handleMaxGroupCountException(){
        return "{}";
    }

}
