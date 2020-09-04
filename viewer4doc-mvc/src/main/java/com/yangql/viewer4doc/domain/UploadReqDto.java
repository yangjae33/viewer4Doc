package com.yangql.viewer4doc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UploadReqDto {
    private String bucketName;
    private String uploadFileName;
    private String localFileLocation;
}