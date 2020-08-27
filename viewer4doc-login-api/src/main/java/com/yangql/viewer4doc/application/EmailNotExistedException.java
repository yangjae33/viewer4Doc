package com.yangql.viewer4doc.application;

public class EmailNotExistedException extends RuntimeException{
    EmailNotExistedException(String email){
        super("Email is not registered "+email);
    }
}
