package com.yangql.viewer4doc.application;

public class UploadFileNotExistException extends RuntimeException{
    UploadFileNotExistException(){
        super("File is not selected");
    }
}
