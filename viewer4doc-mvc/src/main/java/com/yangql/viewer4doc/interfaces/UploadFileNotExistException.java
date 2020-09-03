package com.yangql.viewer4doc.interfaces;

public class UploadFileNotExistException extends RuntimeException{
    UploadFileNotExistException(){
        super("File is not selected");
    }
}
