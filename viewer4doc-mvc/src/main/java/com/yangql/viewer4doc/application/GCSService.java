//package com.yangql.viewer4doc.application;
//
//import com.google.cloud.storage.Acl;
//import com.google.cloud.storage.BlobInfo;
//import com.google.cloud.storage.Storage;
//import com.yangql.viewer4doc.domain.UploadReqDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.Arrays;
//
//@Service
//@RequiredArgsConstructor
//public class GCSService {
//
//    private final Storage storage;
//
//    @SuppressWarnings("deprecation")
//    public BlobInfo uploadFileGCS(UploadReqDto uploadReqDto) throws FileNotFoundException {
//        BlobInfo blobInfo = storage.create(
//                BlobInfo.newBuilder(uploadReqDto.getBucketName(), uploadReqDto.getUploadFileName())
//                        .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllAuthenticatedUsers(), Acl.Role.READER))))
//                        .build(),
//                new FileInputStream(uploadReqDto.getLocalFileLocation()));
//
//        return blobInfo;
//    }
//}
