package com.yangql.viewer4doc.application;

public class MaxFileSizeException  extends RuntimeException {
    MaxFileSizeException(){
        super("Exceed max file size");
    }
}
