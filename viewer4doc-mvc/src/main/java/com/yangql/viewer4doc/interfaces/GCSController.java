package com.yangql.viewer4doc.interfaces;

import com.google.cloud.storage.BlobInfo;
import com.yangql.viewer4doc.application.GCSService;
import com.yangql.viewer4doc.domain.UploadReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class GCSController {

    private final GCSService GCSService;

    @PostMapping("gcs/upload")
    public ResponseEntity localUploadToStorage(@RequestBody UploadReqDto uploadReqDto) throws IOException {
        BlobInfo fileFromGCS = GCSService.uploadFileGCS(uploadReqDto);
        return ResponseEntity.ok(fileFromGCS.toString());
    }
}
