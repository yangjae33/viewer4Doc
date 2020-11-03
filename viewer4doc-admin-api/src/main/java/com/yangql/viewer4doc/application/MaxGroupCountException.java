package com.yangql.viewer4doc.application;

public class MaxGroupCountException extends RuntimeException {
    MaxGroupCountException(){
        super("Group count >= 5");
    }
}
