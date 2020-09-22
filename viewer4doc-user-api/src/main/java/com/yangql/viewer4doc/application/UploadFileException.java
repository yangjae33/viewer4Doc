package com.yangql.viewer4doc.application;

public class UploadFileException extends RuntimeException {
    UploadFileException(){
        super("File is not selected");
    }
}
