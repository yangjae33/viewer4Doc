package com.yangql.viewer4doc.interfaces;

import com.yangql.viewer4doc.application.UploadFileNotExistException;
import com.yangql.viewer4doc.application.UploadWithInvalidExtensionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UploadErrorAdvice {
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UploadFileNotExistException.class)
    public String handleUploadFileNotExist(){
        return "{}";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UploadWithInvalidExtensionException.class)
    public String handleUploadWithInvalidExtension(){
        return "{}";
    }
}
