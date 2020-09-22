package com.yangql.viewer4doc.application;

public class UploadWithInvalidExtensionException extends RuntimeException{
    UploadWithInvalidExtensionException(String fileName){
        super("Invalid File Extension "+fileName);
    }
}
